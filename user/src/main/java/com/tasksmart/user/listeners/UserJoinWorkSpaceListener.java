package com.tasksmart.user.listeners;

import com.tasksmart.sharedLibrary.dtos.messages.UserJoinProjectMessage;
import com.tasksmart.sharedLibrary.dtos.messages.UserJoinWorkSpaceMessage;
import com.tasksmart.user.services.UserInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@KafkaListener(id = "workspace-join-group",topics = {"workspace-add-member"})
public class UserJoinWorkSpaceListener {

    private final UserInternalService userInternalService;

    @KafkaHandler
    public void handleJoinProject(UserJoinWorkSpaceMessage userJoinWorkSpaceMessage) {
        userInternalService.joinWorkSpace(userJoinWorkSpaceMessage);
    }
}