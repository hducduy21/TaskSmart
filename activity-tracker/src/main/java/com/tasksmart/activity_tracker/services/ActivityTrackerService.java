package com.tasksmart.activity_tracker.services;

import com.tasksmart.sharedLibrary.dtos.messages.WorkSpaceMessage;
import com.tasksmart.sharedLibrary.models.enums.EActivityType;

public interface ActivityTrackerService {
    void createWorkSpaceActivity(String userId, String workSpaceId, EActivityType activityType);

    void deleteWorkSpaceActivity(WorkSpaceMessage workSpaceMessage);
}
