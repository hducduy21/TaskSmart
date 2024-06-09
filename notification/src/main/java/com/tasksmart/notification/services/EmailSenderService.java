package com.tasksmart.notification.services;

import com.tasksmart.sharedLibrary.dtos.messages.UserMessage;

public interface EmailSenderService {
    void sendVerificationEmail(String toEmail, String code);
    void sendWelcomeEmail(UserMessage userMessage);
}
