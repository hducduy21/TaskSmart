package com.tasksmart.workspace.services.impls;

import com.tasksmart.workspace.dtos.request.CardCreationRequest;
import com.tasksmart.workspace.dtos.request.ListCardCreationRequest;
import com.tasksmart.workspace.dtos.request.ProjectRequest;
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
import org.springframework.stereotype.Service;

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

    /** {@inheritDoc} */
    @Override
    public List<ProjectGeneralResponse> getAllProject() {
        return projectRepository.findAll().stream().map(this::getProjectGeneralResponse).toList();
    }

    /** {@inheritDoc} */
    @Override
    public List<ProjectGeneralResponse> getAllProjectByWorkSpace(String workSpaceId){
        return projectRepository.findByWorkSpaceId(workSpaceId).stream().map(this::getProjectGeneralResponse).toList();
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
    public ProjectGeneralResponse createPersonalProject(ProjectRequest projectRequest){
        //Get user id from token
        String userId = authenticationUtils.getUserIdAuthenticated();

        //Convert dto to entity
        Project project = modelMapper.map(projectRequest, Project.class);

        //Fetch to get user info
        UserGeneralResponse userGeneralResponse = userClient.getUserGeneralResponse(userId);
        UserRelation userRelation = modelMapper.map(userGeneralResponse, UserRelation.class);
        userRelation.setRole(EUserRole.Owner);

        //Fill user information to project
        project.setWorkSpaceId(userGeneralResponse.getPersonalWorkSpace());
        project.setOwner(userRelation);

        //Save to database
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
        boolean isProjectExist = projectRepository.existsById(projectId);
        if(!isProjectExist){
            throw new ResourceNotFound("Project not found!");
        }
        //check authorization
        return listCardService.createListCard(projectId, listCardCreationRequest);
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
        projectResponse.setListCards(listCardService.getAllListCardByProject(project.getId()));
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