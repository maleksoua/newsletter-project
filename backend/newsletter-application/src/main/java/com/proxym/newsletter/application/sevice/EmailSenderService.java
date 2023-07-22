package com.proxym.newsletter.application.sevice;

import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;

public interface EmailSenderService {
    ResponseEntity<Void> sendEmail(String to, String subject, String message) throws MessagingException;




}
