package com.proxym.newsletter.application.sevice.impl;

import com.proxym.newsletter.application.sevice.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {


        private final JavaMailSender mailSender;


        @Override
        public ResponseEntity<Void> sendEmail(String to, String subject, String message) throws MessagingException {

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            try {
            messageHelper.setFrom("maleksoua123@gmail.com");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(message, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Handle exception
        }
            return null;
        }
}
