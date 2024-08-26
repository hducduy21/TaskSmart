package com.tasksmart.notification.services;

import com.tasksmart.sharedLibrary.dtos.messages.UserMessage;

/**
 * Interface for EmailSenderService
 * This interface provides methods to send emails.
 * @author Duy Hoang
 */
public interface EmailSenderService {
    /**
     * Sends a verification email to the given email address.
     * @param toEmail The email address to send the verification email to.
     * @param code The code to verify the email address with.
     */
    void sendVerificationEmail(String toEmail, String code);

    /**
     * Sends a welcome email to the given user.
     * @param userMessage The user to send the welcome email to.
     */
    void sendWelcomeEmail(UserMessage userMessage);
}
