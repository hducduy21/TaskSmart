package com.example.workspace.services.impls;

import com.example.workspace.models.UserRelation;
import com.example.workspace.models.WorkSpace;
import com.example.workspace.models.enums.EUserRole;
import com.example.workspace.models.enums.EWorkSpaceType;
import com.example.workspace.repositories.WorkSpaceRepository;
import com.example.workspace.services.ProjectService;
import com.example.workspace.services.WorkSpaceInternalService;
import com.tasksmart.sharedLibrary.dtos.messages.UserMessage;
import com.tasksmart.sharedLibrary.dtos.responses.WorkSpaceGeneralResponse;
import com.tasksmart.sharedLibrary.exceptions.InternalServerError;
import com.tasksmart.sharedLibrary.exceptions.UnauthenticateException;
import com.tasksmart.sharedLibrary.repositories.httpClients.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkSpaceInternalServiceImpl implements WorkSpaceInternalService {
    private final WorkSpaceRepository workSpaceRepository;
    private final ModelMapper modelMapper;
    private final ProjectService projectService;
    private final UserClient userClient;

    @Override
    public WorkSpaceGeneralResponse getWorkSpaceById(String workspaceId) {
        return null;
    }

    @Override
    public WorkSpaceGeneralResponse createPersonalWorkSpace(String userId, String name, String Username) {
        UserRelation userRelation = UserRelation.builder()
                .userId(userId)
                .name(name)
                .username(Username)
                .role(EUserRole.Owner)
                .build();

        WorkSpace workSpace = WorkSpace.builder()
                .name(name + "'s workspace")
                .description("Personal workspace of " + name)
                .type(EWorkSpaceType.Personal)
                .users(List.of(userRelation))
                .build();

        workSpaceRepository.save(workSpace);
        return getWorkSpaceGeneralResponse(workSpace);
    }

    @Override
    public void updateUsersInWorkSpace(UserMessage userMessage) {
        String workSpacePersonal = userMessage.getPersonalWorkSpace().getId();
        this.updateUserInfomation(workSpacePersonal,userMessage);

        userMessage.getWorkspaces().forEach(workSpace -> this.updateUserInfomation(workSpace.getId(), userMessage));
    }

    public void updateUserInfomation(String workSpaceId,UserMessage userMessage){
        Optional<WorkSpace> workSpaceOptional = workSpaceRepository.findById(workSpaceId);
        if(workSpaceOptional.isPresent()){
            WorkSpace workSpace = workSpaceOptional.get();
            workSpace.getUsers().forEach(userRelation ->
                    {
                        if(StringUtils.equals(userRelation.getUserId(), userMessage.getId())){
                            userRelation.setName(userMessage.getName());
                            userRelation.setUsername(userMessage.getProfileImageId());
                            userRelation.setUsername(userMessage.getUsername());
                        }
                    });

            if(workSpace.getType().equals(EWorkSpaceType.Personal)){
                workSpace.setName(userMessage.getName() + "'s workspace");
                workSpace.setDescription("Personal workspace of " + userMessage.getName());
            }

            workSpaceRepository.save(workSpace);
            log.info("User-{} in workspace-{} updated information", userMessage.getId(), workSpaceId);
        }else{
            log.error("WorkSpace-{} not found to update user-{}", workSpaceId, userMessage.getId());
        }
    }

    private WorkSpaceGeneralResponse getWorkSpaceGeneralResponse(WorkSpace workSpace) {
        return modelMapper.map(workSpace, WorkSpaceGeneralResponse.class);
    }
}
