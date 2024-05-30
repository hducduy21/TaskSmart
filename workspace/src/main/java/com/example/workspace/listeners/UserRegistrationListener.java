package com.example.workspace.listeners;

import com.tasksmart.sharedLibrary.dtos.messages.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "user-registration-group",topics = {"user-registration"})
public class UserRegistrationListener {
    /**
     * This method listens for user registration events.
     *
     * @param userMessage The user registration event.
     */
    @KafkaHandler
    public void handleUserMessageMessage(UserMessage userMessage) {
        System.out.println("User " + userMessage.getName() + " has been registered.");
    }

}
