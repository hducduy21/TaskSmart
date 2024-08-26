package com.tasksmart.activity_tracker.services;

import com.tasksmart.sharedLibrary.dtos.messages.ProjectAccess;
import com.tasksmart.sharedLibrary.dtos.messages.WorkSpaceMessage;
import com.tasksmart.sharedLibrary.dtos.responses.ProjectAccessResponse;
import com.tasksmart.sharedLibrary.models.enums.EActivityType;

import java.util.List;
/**
 * Service for activity tracker to handle activity related operations
 */
public interface ActivityTrackerService {
    /**
     * Get recent projects
     *
     * @return List of ProjectAccessResponse
     */
    List<ProjectAccessResponse> getProjectRecent();

    /**
     * Create project access activity
     *
     * @param projectAccess Project access event
     */
    void createProjectAccessActivity(ProjectAccess projectAccess);

    /**
     * Create workspace activity
     *
     * @param userId User id
     * @param workSpaceId Workspace id
     * @param activityType Activity type
     */
    void createWorkSpaceActivity(String userId, String workSpaceId, EActivityType activityType);

    /**
     * Delete workspace activity
     *
     * @param workSpaceMessage Workspace message
     */
    void deleteWorkSpaceActivity(WorkSpaceMessage workSpaceMessage);
}
