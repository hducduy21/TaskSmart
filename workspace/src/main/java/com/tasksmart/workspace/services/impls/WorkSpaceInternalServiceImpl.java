package com.tasksmart.workspace.services.impls;

import com.tasksmart.workspace.models.UserRelation;
import com.tasksmart.workspace.models.WorkSpace;
import com.tasksmart.workspace.models.enums.EUserRole;
import com.tasksmart.workspace.models.enums.EWorkSpaceType;
import com.tasksmart.workspace.repositories.WorkSpaceRepository;
import com.tasksmart.workspace.services.ProjectService;
import com.tasksmart.workspace.services.WorkSpaceInternalService;
import com.tasksmart.sharedLibrary.dtos.messages.UserMessage;
import com.tasksmart.sharedLibrary.dtos.responses.WorkSpaceGeneralResponse;
import com.tasksmart.sharedLibrary.repositories.httpClients.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * WorkSpaceInternalServiceImpl class for managing Work Space operations.
 * This class is used to manage the internal operations of Work Space.
 *
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WorkSpaceInternalServiceImpl implements WorkSpaceInternalService {
    private final WorkSpaceRepository workSpaceRepository;
    private final ModelMapper modelMapper;

    /** {@inheritDoc} */
    @Override
    public WorkSpaceGeneralResponse getWorkSpaceById(String workspaceId) {
        return null;
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public void updateUsersInWorkSpace(UserMessage userMessage) {
        WorkSpace workSpace = workSpaceRepository.findByUserIdAndType(userMessage.getId(), EWorkSpaceType.Personal.name())
                .orElseThrow(() -> new RuntimeException("Personal workspace not found for user-" + userMessage.getId()));

        workSpace.setName(userMessage.getName() + "'s workspace");
        workSpaceRepository.save(workSpace);

        String workSpacePersonal = userMessage.getPersonalWorkSpace().getId();
        this.updateUserInfomation(workSpacePersonal,userMessage);

        userMessage.getWorkspaces().forEach(wsp -> this.updateUserInfomation(wsp.getId(), userMessage));


    }

    /**
     * Update user information in workspace
     * @param workSpaceId workspace id
     * @param userMessage user message
     */
    public void updateUserInfomation(String workSpaceId,UserMessage userMessage){
        Optional<WorkSpace> workSpaceOptional = workSpaceRepository.findById(workSpaceId);
        if(workSpaceOptional.isPresent()){
            WorkSpace workSpace = workSpaceOptional.get();
            workSpace.getUsers().forEach(userRelation ->
                    {
                        if(StringUtils.equals(userRelation.getUserId(), userMessage.getId())){
                            userRelation.setName(userMessage.getName());
                            userRelation.setProfileImagePath(userMessage.getProfileImagePath());
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

    /**
     * Get WorkSpaceGeneralResponse from WorkSpace.
     *
     * @param workSpace the WorkSpace.
     * @return the WorkSpaceGeneralResponse.
     */
    private WorkSpaceGeneralResponse getWorkSpaceGeneralResponse(WorkSpace workSpace) {
        return modelMapper.map(workSpace, WorkSpaceGeneralResponse.class);
    }
}
