package com.tasksmart.notification.services.impls;

import com.tasksmart.notification.services.EmailSenderService;
import com.tasksmart.sharedLibrary.dtos.messages.UserMessage;
import com.tasksmart.sharedLibrary.exceptions.InternalServerError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Implementation of EmailSenderService
 * This class provides methods to send emails.
 * @author Duy Hoang
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;

    /** {@inheritDoc} */
    @Override
    public void sendVerificationEmail(String toEmail, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("tasksmart2d@gmail.com");
            message.setTo(toEmail);
            message.setSubject("TaskSmart Email Verification");
            message.setText("Your verification code is: " + code);
            mailSender.send(message);
        }catch (Exception e) {
            log.error("Error sending verification email to " + toEmail);
            throw new InternalServerError("Error sending verification email to " + toEmail);
        }
    }

    /** {@inheritDoc} */
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tasksmart2d@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
        log.info("Email sent to " + to);
    }

    /** {@inheritDoc} */
    public void sendWelcomeEmail(UserMessage userMessage) {
        System.out.println("Sending welcome email to " + userMessage.getEmail());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("tasksmart2d@gmail.com");
            message.setTo(userMessage.getEmail());
            message.setSubject("Welcome to TaskSmart - from Double2D Corp");
            message.setText("Welcome  "+ userMessage.getName() + " to TaskSmart. We are excited to have you on board.");
            mailSender.send(message);
            log.info("Welcome email sent to " + userMessage.getName());
        }catch (Exception e) {
            log.error("Error sending welcome email to " + userMessage.getEmail());
            throw new InternalServerError("Error sending welcome email to " + userMessage.getEmail());
        }
    }
}
