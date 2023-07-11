package com.proxym.newsletter.application.sevice.impl;

import com.proxym.newsletter.application.sevice.EmailSenderService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailSenderServiceImpl implements EmailSenderService {
private final JavaMailSender mailSender;
public EmailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Override
    public void sendEmail(String to, String subject, String message) {

        SimpleMailMessage simpleMailMessage= new SimpleMailMessage();
        simpleMailMessage.setFrom("maleksoua123@gmail.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(message);
        simpleMailMessage.setSubject(subject);
        this.mailSender.send(simpleMailMessage);

    }
}
