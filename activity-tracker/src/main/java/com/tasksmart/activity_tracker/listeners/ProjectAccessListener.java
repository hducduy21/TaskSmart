package com.tasksmart.activity_tracker.listeners;

import com.tasksmart.activity_tracker.services.ActivityTrackerService;
import com.tasksmart.sharedLibrary.dtos.messages.ProjectAccess;
import com.tasksmart.sharedLibrary.dtos.messages.ProjectMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Listener for project access events
 *
 * @author Duy Hoang
 */
@Component
@RequiredArgsConstructor
@KafkaListener(id = "project-access-group",topics = {"project-access"})
public class ProjectAccessListener {
    /**
     * Activity service
     */
    private final ActivityTrackerService activityService;

    /**
     * Handle project access event
     *
     * @param projectAccess Project access event
     */
    @KafkaHandler
    public void handleProjectAccess(ProjectAccess projectAccess) {
        System.out.println("Listen event project "+ projectAccess.getProjectId() +" from user-" + projectAccess.getUserId());
        activityService.createProjectAccessActivity(projectAccess);
    }
}
