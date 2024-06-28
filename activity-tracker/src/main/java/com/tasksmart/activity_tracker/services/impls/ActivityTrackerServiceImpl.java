package com.tasksmart.activity_tracker.services.impls;

import com.tasksmart.activity_tracker.models.Activity;
import com.tasksmart.activity_tracker.repositories.ActivityRepository;
import com.tasksmart.activity_tracker.services.ActivityTrackerService;
import com.tasksmart.sharedLibrary.dtos.messages.WorkSpaceMessage;
import com.tasksmart.sharedLibrary.models.enums.EActivityType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityTrackerServiceImpl implements ActivityTrackerService {
    private final ActivityRepository activityRepository;

    @Override
    public void createWorkSpaceActivity(String userId, String workSpaceId, EActivityType activityType) {
        Activity activity = Activity.builder()
                .initiatorId(userId)
                .workspaceId(workSpaceId)
                .type(activityType)
                .build();

        log.info("Activity created: {}", activity);
        activityRepository.save(activity);
    }

    @Override
    public void deleteWorkSpaceActivity(WorkSpaceMessage workSpaceMessage) {
        activityRepository.deleteAllByWorkspaceId(workSpaceMessage.getId());
    }
}
