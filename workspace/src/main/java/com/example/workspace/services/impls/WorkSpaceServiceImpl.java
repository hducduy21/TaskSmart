package com.example.workspace.services.impls;

import com.example.workspace.dtos.request.ProjectRequest;
import com.example.workspace.dtos.request.WorkSpaceRequest;
import com.example.workspace.dtos.response.*;
import com.example.workspace.models.Invitation;
import com.example.workspace.models.Project;
import com.example.workspace.models.UserRelation;
import com.example.workspace.models.WorkSpace;
import com.example.workspace.models.enums.EUserRole;
import com.example.workspace.models.enums.EWorkSpaceType;
import com.example.workspace.repositories.WorkSpaceRepository;
import com.example.workspace.services.ProjectService;
import com.example.workspace.services.WorkSpaceService;
import com.tasksmart.sharedLibrary.dtos.messages.ProjectMessage;
import com.tasksmart.sharedLibrary.dtos.messages.UserJoinProjectMessage;
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

    @Override
    public List<WorkSpaceGeneralResponse> getAllWorkSpace() {
        return workSpaceRepository.findAll().stream().map(this::getWorkSpaceGeneralResponse).toList();
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

        WorkSpace workspace = modelMapper.map(workSpaceRequest, WorkSpace.class);
        workspace.setType(EWorkSpaceType.Private);
        workspace.setOwner(userRelation);
        workSpaceRepository.save(workspace);

        //kafka to user service add workspace to user
        kafkaTemplate.send("workspace-creation",modelMapper.map(workspace, WorkSpaceMessage.class));
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
        project.setWorkSpaceId(workSpaceId);

        kafkaTemplate.send("project-creation",modelMapper.map(project, ProjectMessage.class));
        return projectService.saveProject(project);
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

        kafkaTemplate.send("workspace-updation",modelMapper.map(workSpace, WorkSpaceMessage.class));

        return getWorkSpaceGeneralResponse(workSpace);
    }

    @Override
    public void deleteWorkSpace(String workSpaceId) {
        WorkSpace workSpace = workSpaceRepository.findById(workSpaceId).orElseThrow(
                ()->new ResourceNotFound("WorkSpace not found!")
        );
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

    public WorkSpaceGeneralResponse getWorkSpaceGeneralResponse(WorkSpace workSpace){
        return modelMapper.map(workSpace, WorkSpaceGeneralResponse.class);
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
        UserJoinProjectMessage userJoinProjectMessage = UserJoinProjectMessage.builder()
                .id(workSpace.getId())
                .name(workSpace.getName())
                .userId(userId)
                .build();
        kafkaTemplate.send("workspace-add-member", userJoinProjectMessage);

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


    public WorkSpaceResponse getWorkSpaceResponse(WorkSpace workSpace){
        WorkSpaceResponse workSpaceResponse = modelMapper.map(workSpace, WorkSpaceResponse.class);
        workSpaceResponse.setProjects(projectService.getAllProjectByWorkSpace(workSpace.getId()));
        return workSpaceResponse;
    }

    private UserRelation getUserRelation(String userId){
        UserGeneralResponse userGeneralResponse = userClient.getUserGeneralResponse(userId);
        return modelMapper.map(userGeneralResponse, UserRelation.class);
    }
}
