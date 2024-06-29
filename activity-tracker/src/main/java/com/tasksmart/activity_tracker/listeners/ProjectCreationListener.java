package com.tasksmart.activity_tracker.listeners;

import com.tasksmart.activity_tracker.services.ActivityTrackerService;
import com.tasksmart.sharedLibrary.dtos.messages.ProjectMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "project-creation-group",topics = {"project-creation"})
public class ProjectCreationListener {

    private final ActivityTrackerService activityService;

    @KafkaHandler
    public void handleProjectCreation(ProjectMessage projectMessage) {
//        activityService.createProject(projectMessage);
    }
}
