package com.tasksmart.user.listeners;

import com.tasksmart.user.services.UserInternalService;
import com.tasksmart.sharedLibrary.dtos.messages.WorkSpaceMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "workspace-creation-group",topics = {"workspace-creation"})
public class WorkSpaceCreationListener {

    private final UserInternalService userInternalService;

    @KafkaHandler
    public void handleWorkSpaceCreation(WorkSpaceMessage workSpaceMessage) {
        System.out.println("WorkSpace creation message: " + workSpaceMessage.getId());
        userInternalService.createWorkSpace(workSpaceMessage);
    }
}
