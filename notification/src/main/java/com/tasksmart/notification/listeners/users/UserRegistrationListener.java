package com.tasksmart.notification.listeners.users;

import com.tasksmart.notification.services.EmailSenderService;
import com.tasksmart.sharedLibrary.dtos.messages.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * This class listens for user registration events.
 * When a user is registered, this class sends a welcome email to the user.

 *@author Duy Hoang
 */
@Component
@RequiredArgsConstructor
@KafkaListener(id = "user-registration-group",topics = {"user-registration"})
public class UserRegistrationListener {
    private final EmailSenderService emailSenderService;
    /**
     * This method listens for user registration events.
     *
     * @param userMessage The user registration event.
     */
    @KafkaHandler
    public void handleUserMessageMessage(UserMessage userMessage) {
        emailSenderService.sendWelcomeEmail(userMessage);
        System.out.println("User " + userMessage.getName() + " has been registered.");
    }
}
