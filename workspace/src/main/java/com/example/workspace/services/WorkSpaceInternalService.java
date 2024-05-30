package com.example.workspace.services;

import com.tasksmart.sharedLibrary.dtos.messages.UserMessage;
import com.tasksmart.sharedLibrary.dtos.responses.WorkSpaceGeneralResponse;

/**
 * WorkSpaceInternalService interface for managing Work Space operations.
 *
 * @author Duy Hoang
 */
public interface WorkSpaceInternalService {
    WorkSpaceGeneralResponse getWorkSpaceById(String workspaceId);
    WorkSpaceGeneralResponse createPersonalWorkSpace(String userId, String name, String Username);

    /**
     * Kafka group
     */
    void updateUsersInWorkSpace(UserMessage userMessage);
}
