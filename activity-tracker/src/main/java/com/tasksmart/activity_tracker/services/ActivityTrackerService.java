package com.tasksmart.activity_tracker.services;

import com.tasksmart.sharedLibrary.dtos.messages.ProjectAccess;
import com.tasksmart.sharedLibrary.dtos.messages.WorkSpaceMessage;
import com.tasksmart.sharedLibrary.dtos.responses.ProjectAccessResponse;
import com.tasksmart.sharedLibrary.models.enums.EActivityType;

import java.util.List;

public interface ActivityTrackerService {
    List<ProjectAccessResponse> getProjectRecent();
    void createProjectAccessActivity(ProjectAccess projectAccess);
    void createWorkSpaceActivity(String userId, String workSpaceId, EActivityType activityType);

    void deleteWorkSpaceActivity(WorkSpaceMessage workSpaceMessage);
}
