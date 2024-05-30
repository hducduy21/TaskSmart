package com.example.user.listeners;

import com.example.user.services.UserInternalService;
import com.tasksmart.sharedLibrary.dtos.messages.WorkSpaceMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "workspace-updation-group",topics = {"workspace-updation"})
public class WorkSpaceUpdationListener {

    private final UserInternalService userInternalService;

    @KafkaHandler
    public void handleWorkSpaceUpdation(WorkSpaceMessage workSpaceMessage) {
        userInternalService.updateWorkSpace(workSpaceMessage);
    }
}
