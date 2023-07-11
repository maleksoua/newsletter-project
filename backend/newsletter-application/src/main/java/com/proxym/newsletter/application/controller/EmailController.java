package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.entity.EmailMessage;
import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.sevice.EmailSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class EmailController {

    private final EmailSenderService emailSenderService;


    public EmailController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/send_email")
    public ResponseEntity sendEmail(@RequestBody EmailMessage emailMessage) {
        String[] toList = emailMessage.getTo().split(","); // Diviser la liste des destinataires par des virgules
        for (String to : toList) {
            this.emailSenderService.sendEmail(to.trim(), emailMessage.getSubject(), emailMessage.getMessage());
        }
        return ResponseEntity.ok("Success");
    }


}
