package com.tasksmart.activity_tracker.services.impls;

import com.tasksmart.activity_tracker.models.Activity;
import com.tasksmart.activity_tracker.repositories.ActivityRepository;
import com.tasksmart.activity_tracker.services.ActivityTrackerService;
import com.tasksmart.sharedLibrary.dtos.messages.ProjectAccess;
import com.tasksmart.sharedLibrary.dtos.messages.WorkSpaceMessage;
import com.tasksmart.sharedLibrary.dtos.responses.ProjectAccessResponse;
import com.tasksmart.sharedLibrary.models.enums.EActivityType;
import com.tasksmart.sharedLibrary.utils.AuthenticationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for activity tracker to handle activity related operations
 *
 * @author Duy Hoang
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityTrackerServiceImpl implements ActivityTrackerService {
    /**
     * Activity repository to handle activity related operations
     */
    private final ActivityRepository activityRepository;

    /**
     * Authentication utils to get user id logged in
     */
    private final AuthenticationUtils authenticationUtils;

    /** {@inheritDoc} */
    @Override
    public List<ProjectAccessResponse> getProjectRecent() {
        String userId = authenticationUtils.getUserIdAuthenticated();
        List<Activity> activities = activityRepository.getRecentProject(userId, EActivityType.PROJECT_ACCESS.name());

        if(CollectionUtils.isEmpty(activities))
            return new ArrayList<>();

        return activities.stream()
                .map(activity -> ProjectAccessResponse.builder()
                        .id(activity.getProject().getId())
                        .updatedAt(activity.getUpdatedAt())
                        .build()
                )
                .toList();
    }

    /** {@inheritDoc} */
    @Override
    public void createProjectAccessActivity(ProjectAccess projectAccess) {
        Optional<Activity> activityOptional = activityRepository.findByInitiatorIdAndProjectIdAndType(
                projectAccess.getUserId(),
                projectAccess.getProjectId(),
                EActivityType.PROJECT_ACCESS.name()
        );
        Activity activity;
        if(activityOptional.isPresent()) {
            activity = activityOptional.get();
            activity.setUpdatedAt(LocalDateTime.now());
        }else{
            activity = Activity.builder()
                .initiatorId(projectAccess.getUserId())
                .project(Activity.ProjectActivity.builder()
                        .id(projectAccess.getProjectId())
                        .name(projectAccess.getProjectName())
                        .build()
                )
                .type(EActivityType.PROJECT_ACCESS)
                .build();
        }

        activityRepository.save(activity);
        log.info("Event listen access activity: {}", activity);
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public void deleteWorkSpaceActivity(WorkSpaceMessage workSpaceMessage) {
        activityRepository.deleteAllByWorkspaceId(workSpaceMessage.getId());
    }
}
