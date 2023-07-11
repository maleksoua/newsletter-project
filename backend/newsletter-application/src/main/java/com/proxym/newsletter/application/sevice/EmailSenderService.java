package com.proxym.newsletter.application.sevice;

public interface EmailSenderService {
    void sendEmail(String to,String subject,String message);



}
