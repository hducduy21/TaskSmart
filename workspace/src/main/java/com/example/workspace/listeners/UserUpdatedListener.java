package com.example.workspace.listeners;

import com.example.workspace.services.WorkSpaceInternalService;
import com.tasksmart.sharedLibrary.dtos.messages.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "user-updation-group",topics = {"user-updation"})
public class UserUpdatedListener {
    private final WorkSpaceInternalService workSpaceInternalService;

    /**
     *
     * @param userMessage
     */
    @KafkaHandler
    public void handleUserMessageUpdatedMessage(UserMessage userMessage) {
        workSpaceInternalService.updateUsersInWorkSpace(userMessage);
    }

}
