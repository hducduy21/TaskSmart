package com.tasksmart.user.listeners;

import com.tasksmart.user.services.UserInternalService;
import com.tasksmart.sharedLibrary.dtos.messages.ProjectMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "project-creation-group",topics = {"project-creation"})
public class ProjectCreationListener {
    private final UserInternalService userInternalService;

    @KafkaHandler
    public void handleProjectCreation(ProjectMessage projectMessage) {
        userInternalService.createProject(projectMessage);
    }
}
