package com.tasksmart.workspace.services.impls;

import com.tasksmart.sharedLibrary.dtos.messages.UserJoinWorkSpaceMessage;
import com.tasksmart.sharedLibrary.dtos.responses.CategoryResponse;
import com.tasksmart.sharedLibrary.dtos.messages.UnsplashResponse;
import com.tasksmart.sharedLibrary.dtos.responses.SearchAllResponse;
import com.tasksmart.sharedLibrary.repositories.httpClients.CategoryClient;
import com.tasksmart.sharedLibrary.repositories.httpClients.UnsplashClient;
import com.tasksmart.workspace.dtos.request.ProjectRequest;
import com.tasksmart.workspace.dtos.request.WorkSpaceRequest;
import com.tasksmart.workspace.dtos.request.WorkspaceUpdateImage;
import com.tasksmart.workspace.dtos.response.*;
import com.tasksmart.workspace.models.Invitation;
import com.tasksmart.workspace.models.Project;
import com.tasksmart.workspace.models.UserRelation;
import com.tasksmart.workspace.models.WorkSpace;
import com.tasksmart.workspace.models.enums.EUserRole;
import com.tasksmart.workspace.models.enums.EWorkSpaceType;
import com.tasksmart.workspace.repositories.WorkSpaceRepository;
import com.tasksmart.workspace.services.ProjectService;
import com.tasksmart.workspace.services.WorkSpaceService;
import com.tasksmart.sharedLibrary.dtos.messages.ProjectMessage;
import com.tasksmart.sharedLibrary.dtos.messages.WorkSpaceMessage;
import com.tasksmart.sharedLibrary.dtos.responses.UserGeneralResponse;
import com.tasksmart.sharedLibrary.exceptions.BadRequest;
import com.tasksmart.sharedLibrary.exceptions.Forbidden;
import com.tasksmart.sharedLibrary.exceptions.ResourceNotFound;
import com.tasksmart.sharedLibrary.exceptions.UnauthenticateException;
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

@RequiredArgsConstructor
@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {
    private final WorkSpaceRepository workSpaceRepository;
    private final ModelMapper modelMapper;
    private final ProjectService projectService;
    private final UserClient userClient;
    private final AuthenticationUtils authenticationUtils;
    private final KafkaTemplate<String,Object> kafkaTemplate;
    private final CategoryClient categoryClient;
    private final UnsplashClient unsplashClient;

    @Override
    public List<WorkSpaceGeneralResponse> getAllWorkSpace() {
        String userId = authenticationUtils.getUserIdAuthenticated();
        return workSpaceRepository.findByUserId(userId).stream().map(this::getWorkSpaceGeneralResponse).toList();
    }

    @Override
    public WorkSpaceResponse getWorkSpaceById(String Id) {
        WorkSpace workSpace = workSpaceRepository.findById(Id).orElseThrow(
                ()->new ResourceNotFound("WorkSpace not found!")
        );
        return getWorkSpaceResponse(workSpace);
    }

    @Override
    public WorkSpaceGeneralResponse createWorkSpace(WorkSpaceRequest workSpaceRequest) {
        String userId = authenticationUtils.getUserIdAuthenticated();

        UserRelation userRelation = this.getUserRelation(userId);
        userRelation.setRole(EUserRole.Owner);

        WorkSpace workspace = WorkSpace.builder()
                .name(workSpaceRequest.getName())
                .description(workSpaceRequest.getDescription())
                .categoryId(workSpaceRequest.getCategoryId()).build();
        workspace.setType(EWorkSpaceType.Private);
        workspace.setOwner(userRelation);
        workSpaceRepository.save(workspace);

        //kafka to user service add workspace to user
        WorkSpaceGeneralResponse workSpaceGeneralResponse = getWorkSpaceGeneralResponse(workspace);
        WorkSpaceMessage workSpaceMessage = modelMapper.map(workspace, WorkSpaceMessage.class);
        workSpaceMessage.setInteractorId(userId);
        workSpaceMessage.setBackgroundUnsplash(workSpaceGeneralResponse.getBackgroundUnsplash());
        kafkaTemplate.send("workspace-creation",workSpaceMessage);
        return getWorkSpaceGeneralResponse(workspace);
    }

    @Override
    public ProjectGeneralResponse createProject(String workSpaceId, ProjectRequest projectRequest) {
        if (!workSpaceRepository.existsById(workSpaceId)){
            throw new ResourceNotFound("WorkSpace not found!");
        }

        String userId = authenticationUtils.getUserIdAuthenticated();

        UserRelation userRelation = this.getUserRelation(userId);
        userRelation.setRole(EUserRole.Owner);

        Project project = modelMapper.map(projectRequest, Project.class);
        project.setOwner(userRelation);
        project.setWorkspaceId(workSpaceId);

        ProjectGeneralResponse projectGeneralResponse = getProjectGeneralResponse(project);

        ProjectMessage projectMessage = modelMapper.map(project, ProjectMessage.class);
        projectMessage.setInteractorId(userId);
        projectMessage.setBackgroundColor(projectGeneralResponse.getBackgroundColor());
        projectMessage.setBackgroundUnsplash(projectGeneralResponse.getBackgroundUnsplash());
        kafkaTemplate.send("project-creation",projectMessage);

        return projectGeneralResponse;
    }

    @Override
    public WorkSpaceGeneralResponse editWorkSpace(String workSpaceId,WorkSpaceRequest workSpaceRequest) {
        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId).orElseThrow(
                ()->new ResourceNotFound("WorkSpace not found!")
        );
        UserRelation owner = workSpace.getOwner();

        String userId = authenticationUtils.getUserIdAuthenticated();

        if(!StringUtils.equals(owner.getUserId(), userId)){
            throw new UnauthenticateException("You are not authorized to edit this workspace!");
        }

        workSpace.setName(workSpaceRequest.getName());
        workSpace.setDescription(workSpaceRequest.getDescription());
        workSpaceRepository.save(workSpace);


        WorkSpaceGeneralResponse workSpaceGeneralResponse = getWorkSpaceGeneralResponse(workSpace);
        WorkSpaceMessage workSpaceMessage = modelMapper.map(workSpace, WorkSpaceMessage.class);
        workSpaceMessage.setInteractorId(userId);
        kafkaTemplate.send("workspace-updation",workSpaceMessage);
        return getWorkSpaceGeneralResponse(workSpace);
    }

    @Override
    public void deleteWorkSpace(String workSpaceId) {
        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId).orElseThrow(
                ()->new ResourceNotFound("WorkSpace not found!")
        );

        projectService.deleteAllProjectByWorkSpace(workSpaceId);

        UserRelation owner = workSpace.getOwner();

        String userId = authenticationUtils.getUserIdAuthenticated();

        if(!StringUtils.equals(owner.getUserId(), userId)){
            throw new UnauthenticateException("You are not authorized to delete this workspace!");
        }
        workSpaceRepository.deleteById(workSpaceId);
    }

    @Override
    public boolean isWorkSpaceExist(String workSpaceId) {
        return workSpaceRepository.existsById(workSpaceId);
    }

    @Override
    public WorkSpaceResponse joinWorkSpaceByInviteCode(String workspaceId, String inviteCode) {
        //Get user id from token
        String userId = authenticationUtils.getUserIdAuthenticated();

        //Get workspace by id
        WorkSpace workSpace = workSpaceRepository.findById(workspaceId).orElseThrow(
                ()->new ResourceNotFound("WorkSpace not found!")
        );

        //Check is workspace public
        if (!workSpace.getInvitation().isPublic()) {
            throw new Forbidden("WorkSpace is not public!");
        }

        //Check invite code
        if(!StringUtils.equals(workSpace.getInvitation().getCode(), inviteCode)){
            throw new Forbidden("Invite code is not correct!");
        }

        //Check user is already joined
        for(UserRelation userRelation: workSpace.getUsers()){
            if(StringUtils.equals(userRelation.getUserId(), userId)){
                return getWorkSpaceResponse(workSpace);
            }
        }

        //Add user to workspace
        workSpace.addMembers(getUserRelation(userId));
        workSpaceRepository.save(workSpace);

        //Notifications to other applications to said that a user has been joined to project
        UserJoinWorkSpaceMessage userJoinWorkSpaceMessage = UserJoinWorkSpaceMessage.builder()
                .id(workSpace.getId())
                .name(workSpace.getName())
                .userId(userId)
                .build();
        kafkaTemplate.send("workspace-add-member", userJoinWorkSpaceMessage);

        return getWorkSpaceResponse(workSpace);
    }

    @Override
    public InviteCodeResponse updateInviteCode(String projectId, Boolean isPublic, Boolean refresh) {
        if(ObjectUtils.allNull(isPublic, refresh)){
            throw new BadRequest("Nothing to update!");
        }

        //Get user id from token
        String userId = authenticationUtils.getUserIdAuthenticated();

        //Get workspace by id
        WorkSpace workSpace = workSpaceRepository.findById(projectId).orElseThrow(
                ()->new ResourceNotFound("WorkSpace not found!")
        );

        //Check user is owner of workspace
        if(!StringUtils.equals(workSpace.getOwner().getUserId(), userId)){
            throw new Forbidden("You do not have permission to refresh invite code!");
        }

        Invitation invitation = workSpace.getInvitation();
        if (ObjectUtils.isNotEmpty(refresh) && refresh) {
            //Refresh invite code
            invitation.setCode(UUID.randomUUID().toString());
        }

        if (ObjectUtils.isNotEmpty(isPublic)) {
            invitation.setPublic(isPublic);
        }

        workSpace.setInvitation(invitation);
        workSpaceRepository.save(workSpace);

        return InviteCodeResponse.builder().inviteCode(invitation.getCode()).build();
    }

    @Override
    public WorkSpaceGeneralResponse setUnsplashBackground(String workSpaceId, WorkspaceUpdateImage unsplash) {
        String userId = authenticationUtils.getUserIdAuthenticated();

        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId).orElseThrow(
                ()->new ResourceNotFound("WorkSpace not found!")
        );

        if(!StringUtils.equals(workSpace.getOwner().getUserId(), userId)){
            throw new UnauthenticateException("You do not have permission to make this request!");
        }

        workSpace.setBackgroundUnsplash(unsplash.getBackgroundUnsplash());
        workSpaceRepository.save(workSpace);
        return getWorkSpaceGeneralResponse(workSpace);
    }

    @Override
    public SearchAllResponse search(String query) {
        String userId = authenticationUtils.getUserIdAuthenticated();
        List<SearchAllResponse.WorkSpaceResponse> searchAllWSPResponses = workSpaceRepository.findByUserIdAndNameContain(userId, query).stream()
                .map(workSpace -> SearchAllResponse.WorkSpaceResponse.builder()
                        .id(workSpace.getId())
                        .name(workSpace.getName())
                        .backgroundUnsplash(workSpace.getBackgroundUnsplash())
                        .build())
                .toList();
        SearchAllResponse searchAllProjectResponse = projectService.search(query);
        searchAllProjectResponse.setWorkspaces(searchAllWSPResponses);
        return searchAllProjectResponse;
    }

    public WorkSpaceGeneralResponse getWorkSpaceGeneralResponse(WorkSpace workSpace){
        return modelMapper.map(workSpace, WorkSpaceGeneralResponse.class);
    }

    public WorkSpaceResponse getWorkSpaceResponse(WorkSpace workSpace){
        WorkSpaceResponse workSpaceResponse = modelMapper.map(workSpace, WorkSpaceResponse.class);
        workSpaceResponse.setProjects(projectService.getAllProjectByWorkSpace(workSpace.getId()));

        if(!StringUtils.isBlank(workSpace.getCategoryId())){
            CategoryResponse categoryResponse = categoryClient.getCategory(workSpace.getCategoryId());
            workSpaceResponse.setCategory(categoryResponse);
        }
        return workSpaceResponse;
    }

    private UserRelation getUserRelation(String userId){
        UserGeneralResponse userGeneralResponse = userClient.getUserGeneralResponse(userId);
        return modelMapper.map(userGeneralResponse, UserRelation.class);
    }

    public ProjectGeneralResponse getProjectGeneralResponse(Project project){
        return modelMapper.map(project, ProjectGeneralResponse.class);
    }
}
