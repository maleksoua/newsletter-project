package com.proxym.newsletter.application.sevice;

import com.proxym.newsletter.application.entity.EmailRequest;
import jakarta.mail.MessagingException;

public interface EmailSenderService {
    void sendEmail(String to, String subject, String message) throws MessagingException;
    String sendEmailToSubscriber(EmailRequest emailRequest)throws MessagingException;



}
