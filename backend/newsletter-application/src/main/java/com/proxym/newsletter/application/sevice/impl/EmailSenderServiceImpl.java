package com.proxym.newsletter.application.sevice.impl;

import com.proxym.newsletter.application.entity.EmailRequest;
import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.sevice.EmailSenderService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final SubjectRepository subjectRepository;
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String message) throws MessagingException {
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

    }
@Override
    public String sendEmailToSubscriber(EmailRequest emailRequest) throws MessagingException {
    Long subjectId = emailRequest.getSubjectId();
    String language = emailRequest.getLanguage();
    String message = emailRequest.getMessage();
    String subjectEmail = emailRequest.getSubjectEmail();
    Subject subject = subjectRepository.findById(subjectId)
            .orElseThrow(() -> new RuntimeException("Subject not found"));

    String subjectName = subject.getName(); // Get the subject name from the Subject object

    Set<Subscriber> subscribers = subject.getSubscribers();

        if (subscribers.isEmpty()) {
        return "No subscribers found";
    }
    List<String> matchedSubscribers = new ArrayList<>();


        for (Subscriber subscriber : subscribers) {

        if (subscriber.getLanguage().equalsIgnoreCase(language) && subscriber.getSubjects().contains(subject)) {
            String subjectAndCategory = subject.getName() + " - " + subject.getCategory();
            String emailContent = message + " " + subjectAndCategory;
           sendEmail(subscriber.getEmail(),subjectEmail, emailContent);

            matchedSubscribers.add(subscriber.getFirstName());
        }
    }

        if (!matchedSubscribers.isEmpty()) {
        String messagee = "E-mails sent to subscribers with the chosen language and subject: " + String.join(", ", matchedSubscribers);
        return messagee;
    } else {
        return "No subscribers found matching the chosen language and subject.";
    }
}


















}
