package com.proxym.newsletter.application.service;

public interface EmailSenderService {
    void sendEmail(String to, String subject, String message);
}
