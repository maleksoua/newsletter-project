package com.proxym.newsletter.application.sevice;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmailToSubscriber(String email, String subject, String message)throws MessagingException;
}
