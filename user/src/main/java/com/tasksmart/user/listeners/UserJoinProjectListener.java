package com.tasksmart.user.listeners;

import com.tasksmart.user.services.UserInternalService;
import com.tasksmart.sharedLibrary.dtos.messages.UserJoinProjectMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "project-join-group",topics = {"project-add-member"})
public class UserJoinProjectListener {

    private final UserInternalService userInternalService;

    @KafkaHandler
    public void handleJoinProject(UserJoinProjectMessage userJoinProjectMessage) {
        userInternalService.joinProject(userJoinProjectMessage);
    }
}
