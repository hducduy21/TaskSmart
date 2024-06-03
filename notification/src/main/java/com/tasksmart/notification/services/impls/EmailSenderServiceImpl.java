package com.tasksmart.notification.services.impls;

import com.tasksmart.notification.services.EmailSenderService;
import com.tasksmart.sharedLibrary.dtos.messages.UserMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSenderServiceImpl implements EmailSenderService {
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tasksmart2d@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
        log.info("Email sent to " + to);
    }

    public void sendWelcomeEmail(UserMessage userMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tasksmart2d@gmail.com");
        message.setTo(userMessage.getEmail());
        message.setSubject("Welcome to TaskSmart - from Double2D Corp");
        message.setText("Welcome " + userMessage.getName() + " to TaskSmart. We are excited to have you on board.");

        mailSender.send(message);
        log.info("Welcome email sent to " + userMessage.getName());
    }
}
