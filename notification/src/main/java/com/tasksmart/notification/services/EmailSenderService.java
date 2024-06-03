package com.tasksmart.notification.services;

import com.tasksmart.sharedLibrary.dtos.messages.UserMessage;

public interface EmailSenderService {
    void sendWelcomeEmail(UserMessage userMessage);
}
