package com.tasksmart.workspace.services.impls;

import com.tasksmart.sharedLibrary.exceptions.InternalServerError;
import com.tasksmart.sharedLibrary.services.AwsS3Service;
import com.tasksmart.workspace.dtos.request.*;
import com.tasksmart.workspace.dtos.response.*;
import com.tasksmart.workspace.models.Invitation;
import com.tasksmart.workspace.models.Project;
import com.tasksmart.workspace.models.UserRelation;
import com.tasksmart.workspace.models.enums.EUserRole;
import com.tasksmart.workspace.repositories.ProjectRepository;
import com.tasksmart.workspace.services.ListCardService;
import com.tasksmart.workspace.services.ProjectService;
import com.tasksmart.sharedLibrary.dtos.messages.ProjectMessage;
import com.tasksmart.sharedLibrary.dtos.messages.UserJoinProjectMessage;
import com.tasksmart.sharedLibrary.dtos.responses.UserGeneralResponse;
import com.tasksmart.sharedLibrary.exceptions.BadRequest;
import com.tasksmart.sharedLibrary.exceptions.Forbidden;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import com.tasksmart.sharedLibrary.repositories.httpClients.UserClient;
import com.tasksmart.sharedLibrary.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This class implements the ProjectService interface.
 *
 * @author Duy Hoang
 */
@RequiredArgsConstructor
@Service
public class ProjectServiceImpl implements ProjectService {
    /** The Project's Repository instance for interact with the database.*/
    private final ProjectRepository projectRepository;

    /** The ModelMapper instance for mapper dto.*/
    private final ModelMapper modelMapper;

    /** The ListCardService instance.*/
    private final ListCardService listCardService;

    private final UserClient userClient;

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final AuthenticationUtils authenticationUtils;
    private final AwsS3Service awsS3Service;

    /** {@inheritDoc} */
    @Override
    public List<ProjectGeneralResponse> getAllProject() {
        return projectRepository.findAll().stream().map(this::getProjectGeneralResponse).toList();
    }

    /** {@inheritDoc} */
    @Override
    public List<ProjectGeneralResponse> getAllProjectByWorkSpace(String workSpaceId){
        return projectRepository.findByWorkspaceId(workSpaceId).stream().map(this::getProjectGeneralResponse).toList();
    }

    /** {@inheritDoc} */
    @Override
    public ProjectResponse getProjectById(String projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()->new ResourceNotFound("Project not found!")
        );
        return getProjectResponse(project);
    }

    /** {@inheritDoc} */
    @Override
    public ProjectGeneralResponse createProject(ProjectRequest projectRequest){
        //Get user id from token
        String userId = authenticationUtils.getUserIdAuthenticated();

        //Convert dto to entity
        Project project = Project.builder()
                .name(projectRequest.getName())
                .background(projectRequest.getBackground())
                .description(projectRequest.getDescription())
                .workspaceId(projectRequest.getWorkspaceId())
                .build();

        //Fetch to get user info
        UserGeneralResponse userGeneralResponse = userClient.getUserGeneralResponse(userId);
        UserRelation userRelation = modelMapper.map(userGeneralResponse, UserRelation.class);
        userRelation.setRole(EUserRole.Owner);

        //if workspace is empty, set to personal workspace
        if(StringUtils.isBlank(projectRequest.getWorkspaceId())){
            project.setWorkspaceId(userGeneralResponse.getPersonalWorkSpace());
        }

        project.setOwner(userRelation);

        projectRepository.save(project);

        //Notifications to other applications to said that a new project has been created
        kafkaTemplate.send("project-creation",modelMapper.map(project, ProjectMessage.class));

        return getProjectGeneralResponse(project);
    }

    /** {@inheritDoc} */
    @Override
    public ProjectGeneralResponse saveProject(Project project){
        projectRepository.save(project);
        return getProjectGeneralResponse(project);
    }

    /** {@inheritDoc} */
    @Override
    public ProjectGeneralResponse editProject(String projectId, ProjectRequest projectRequest) {
        //Get project by id
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()->new ResourceNotFound("Project not found!")
        );

        //Get user id from token
        String userId = authenticationUtils.getUserIdAuthenticated();
        //Check user is owner of project
        if(!StringUtils.equals(project.getOwner().getUserId(), userId)){
            throw new Forbidden("You do not have permission to edit this project!");
        }

        //Update project
        project.setName(projectRequest.getName());
        project.setDescription(projectRequest.getDescription());
        project.setBackground(projectRequest.getBackground());
        projectRepository.save(project);

        //Notifications to other applications to said that a project has been updated
        kafkaTemplate.send("project-updation", modelMapper.map(project, ProjectMessage.class));
        return getProjectGeneralResponse(project);
    }

    /** {@inheritDoc} */
    @Override
    public ListCardResponse createListCard(String projectId, ListCardCreationRequest listCardCreationRequest){
        Project workSpace = projectRepository.findById(projectId).orElseThrow(
                ()->new ResourceNotFound("WorkSpace not found!")
        );

        ListCardResponse listCardResponse = listCardService.createListCard(projectId, listCardCreationRequest);
        List<String> listCardIds;

        if(CollectionUtils.isEmpty(workSpace.getListCardIds())){
            listCardIds = new ArrayList<>();
        }else {
            listCardIds = workSpace.getListCardIds();
        }

        listCardIds.add(listCardResponse.getId());
        workSpace.setListCardIds(listCardIds);
        projectRepository.save(workSpace);

        return listCardResponse;
    }

    /** {@inheritDoc} */
    @Override
    public CardResponse createCard(String projectId, String listCardId, CardCreationRequest cardCreationRequest){
        boolean isProjectExist = projectRepository.existsById(projectId);
        if(!isProjectExist){
            throw new ResourceNotFound("Project not found!");
        }
        //check authorization
        return listCardService.createCard(projectId, listCardId, cardCreationRequest);
    }

    /** {@inheritDoc} */
    @Override
    public void deleteProject(String projectId) {
        if(!projectRepository.existsById(projectId)){
            throw new ResourceNotFound("Project not found!");
        }
        projectRepository.deleteById(projectId);
    }

    @Override
    public ProjectResponse joinProjectByInviteCode(String projectId, String inviteCode) {
        //Get user id from token
        String userId = authenticationUtils.getUserIdAuthenticated();

        //Get project by id
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()->new ResourceNotFound("Project not found!")
        );

        //Check is project public
        if (!project.getInvitation().isPublic()) {
            throw new Forbidden("Project is not public!");
        }

        //Check invite code
        if(!StringUtils.equals(project.getInvitation().getCode(), inviteCode)){
            throw new Forbidden("Invite code is not correct!");
        }

        //Check user is already joined
        for(UserRelation userRelation: project.getUsers()){
            if(StringUtils.equals(userRelation.getUserId(), userId)){
                return getProjectResponse(project);
            }
        }

        //Add user to project
        project.addMembers(getUserRelation(userId));
        projectRepository.save(project);

        //Notifications to other applications to said that a user has been joined to project
        UserJoinProjectMessage userJoinProjectMessage = UserJoinProjectMessage.builder()
                .id(project.getId())
                .name(project.getName())
                .userId(userId)
                .build();
        kafkaTemplate.send("project-add-member", userJoinProjectMessage);

        return getProjectResponse(project);
    }

    @Override
    public InviteCodeResponse updateInviteCode(String projectId, Boolean isPublic, Boolean refresh) {
        if(ObjectUtils.allNull(isPublic, refresh)){
            throw new BadRequest("Nothing to update!");
        }

        //Get user id from token
        String userId = authenticationUtils.getUserIdAuthenticated();

        //Get project by id
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()->new ResourceNotFound("Project not found!")
        );

        //Check user is owner of project
        if(!StringUtils.equals(project.getOwner().getUserId(), userId)){
            throw new Forbidden("You do not have permission to refresh invite code!");
        }

        Invitation invitation = project.getInvitation();
        if (ObjectUtils.isNotEmpty(refresh) && refresh) {
            //Refresh invite code
            invitation.setCode(UUID.randomUUID().toString());
        }

        if (ObjectUtils.isNotEmpty(isPublic)) {
            invitation.setPublic(isPublic);
        }

        project.setInvitation(invitation);
        projectRepository.save(project);

        return InviteCodeResponse.builder().inviteCode(invitation.getCode()).build();
    }

    @Override
    public ListCardResponse updateListCard(String projectId, String listCardId, ListCardCreationRequest listCardCreationRequest) {
        Project workSpace = projectRepository.findById(projectId).orElseThrow(
                ()->new ResourceNotFound("WorkSpace not found!")
        );

        String userId = authenticationUtils.getUserIdAuthenticated();

        if(!workSpace.getOwner().getUserId().equals(userId)){
            if(workSpace.getUsers().stream().noneMatch(userRelation -> userRelation.getUserId().equals(userId))){
                throw new Forbidden("You are not authorized to update this list card!");
            }
        }

        return listCardService.updateListCard(listCardId, listCardCreationRequest);
    }

    @Override
    public void deleteListCard(String projectId, String listCardId) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()->new ResourceNotFound("WorkSpace not found!")
        );

        String userId = authenticationUtils.getUserIdAuthenticated();

        if(!project.getOwner().getUserId().equals(userId)){
            if(project.getUsers().stream().noneMatch(userRelation -> userRelation.getUserId().equals(userId))){
                throw new Forbidden("You are not authorized to update this list card!");
            }
        }

        listCardService.deleteListCard(listCardId);
        project.getListCardIds().remove(listCardId);
        projectRepository.save(project);
    }

    @Override
    public ProjectResponse moveListCard(String projectId, MoveListCardRequest moveListCardRequest) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()->new ResourceNotFound("Project not found!")
        );

        if(CollectionUtils.isEmpty(project.getListCardIds())){
            throw new BadRequest("Project has no list card!");
        }

        project.getListCardIds().forEach(listCardId -> {
            if(!moveListCardRequest.getIds().contains(listCardId)){
                throw new BadRequest("List card not found!");
            }
        });

        project.setListCardIds(moveListCardRequest.getIds());
        projectRepository.save(project);

        return getProjectResponse(project);
    }

    @Override
    public ProjectResponse moveCard(String projectId, MoveCardRequest moveCardRequest) {
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()->new ResourceNotFound("Project not found!")
        );

        if(CollectionUtils.isEmpty(project.getListCardIds())){
            throw new BadRequest("Project has no list card!");
        }

        List<String> listCardIds = moveCardRequest.getIds().stream().map(MoveCardRequest.MoveCards::getListCardId).toList();

        project.getListCardIds().forEach(listCardId -> {
            if(!listCardIds.contains(listCardId)){
                throw new BadRequest("List card not found!");
            }
        });

        moveCardRequest.getIds().forEach(moveCards -> {
            listCardService.moveCard(moveCards.getListCardId(), moveCards.getCardIds());
        });

        project.setListCardIds(listCardIds);
        projectRepository.save(project);

        return getProjectResponse(project);
    }

    @Override
    public byte[] viewImage(String projectId, String assetId) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        Project project = projectRepository.findById(projectId).orElseThrow(
                ()->new ResourceNotFound("Project not found!")
        );
        if(!project.getUsers().stream().anyMatch(userRelation -> userRelation.getUserId().equals(userId))){
            throw new Forbidden("You are not authorized to view this image!");
        }
        try {
            return awsS3Service.getByte("projects/" + projectId + "/" + assetId);
        }catch (Exception e){
            throw new InternalServerError("Error getting profile image! Please try later.");
        }
    }



    /**
     * Get ProjectGeneralResponse from Project.
     *
     * @param project the Project.
     * @return the ProjectGeneralResponse.
     */
    public ProjectGeneralResponse getProjectGeneralResponse(Project project){
        return modelMapper.map(project, ProjectGeneralResponse.class);
    }

    /**
     * Get ProjectResponse from Project.
     *
     * @param project the Project.
     * @return the ProjectResponse.
     */
    public ProjectResponse getProjectResponse(Project project){
        ProjectResponse projectResponse = modelMapper.map(project, ProjectResponse.class);
        projectResponse.setListCards(listCardService.getListCardByIdIn(project.getListCardIds()));
        projectResponse.setInviteCode(getInviteCode(project.getInvitation()));
        return projectResponse;
    }

    /**
     * Get invite code from Invitation.
     *
     * @param invitation the Invitation.
     * @return the invite code.
     */
    public String getInviteCode(Invitation invitation){
        if(invitation.isPublic()){
            return invitation.getCode();
        }
        return "";
    }

    /**
     * Get UserRelation from UserGeneralResponse.
     *
     * @param userId the user id.
     * @return the UserRelation.
     */
    private UserRelation getUserRelation(String userId){
        UserGeneralResponse userGeneralResponse = userClient.getUserGeneralResponse(userId);
        return modelMapper.map(userGeneralResponse, UserRelation.class);
    }
}
