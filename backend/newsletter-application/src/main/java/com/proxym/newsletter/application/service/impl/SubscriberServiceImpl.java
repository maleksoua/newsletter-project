package com.proxym.newsletter.application.service.impl;

import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.entity.SubscriptionRequest;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.repository.SubscriberRepository;
import com.proxym.newsletter.application.repository.SubscriptionRequestRepository;
import com.proxym.newsletter.application.service.EmailSenderService;
import com.proxym.newsletter.application.service.SubscriberService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
@Service
@AllArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final SubjectRepository subjectRepository;
    private final EmailSenderService emailSenderService;
    private final SubscriptionRequestRepository subscriptionRequestRepository;

    @Override
    public Subscriber addSubscriber(Subscriber subscriber) throws MessagingException {

        if (subscriber.getSubjects() != null) {
            Set<Subject> attachedSubjects = new HashSet<>();
            for (Subject subject : subscriber.getSubjects()) {
                Subject existingSubject = subjectRepository.findByName(subject.getName());
                if (existingSubject != null) {
                    attachedSubjects.add(existingSubject);
                }
            }
            subscriber.setSubjects(attachedSubjects);
        }
        return subscriberRepository.save(subscriber);
    }

    @Override
    public void validateSubscriber(Subscriber subscriber) throws MessagingException {

        Set<Subject> attachedSubjects = new HashSet<>();

        if (subscriber.getSubjects() != null) {
            attachedSubjects = new HashSet<>();
            for (Subject subject : subscriber.getSubjects()) {
                Subject existingSubject = subjectRepository.findByName(subject.getName());
                if (existingSubject != null) {
                    attachedSubjects.add(existingSubject);
                }
            }
            subscriber.setSubjects(attachedSubjects);
        }

        Subscriber existingSubscriber = subscriberRepository.findByEmail(subscriber.getEmail());
        if (existingSubscriber != null) {
            System.err.println("Subscriber already exists.");
            return;
        }

        StringBuilder subjectNamesBuilder = new StringBuilder();
        for (Subject subject : attachedSubjects) {
            subjectNamesBuilder.append(subject.getName()).append(",");
        }

        String subjectNames = subjectNamesBuilder.toString();
        if (!subjectNames.isEmpty()) {
            subjectNames = subjectNames.substring(0, subjectNames.length() - 1);
        }

        String randomCode = generateRandomCode();
        SubscriptionRequest subscriptionRequestEntity = new SubscriptionRequest();
        subscriptionRequestEntity.setCode(randomCode);
        subscriptionRequestEntity.setEmail(subscriber.getEmail());

        String bodyString = subscriber.getEmail() + "|" +
                subscriber.getLastName() + "|" +
                subscriber.getFirstName() + "|" +
                subscriber.getLanguage() + "|" +
                subjectNames;
        subscriptionRequestEntity.setBody(bodyString);

        subscriptionRequestRepository.save(subscriptionRequestEntity);
        String emailTemplate;

        if (subscriber.getLanguage().name().equals("ENGLISH")) {
            emailTemplate = getEmailTemplateFromFile("C:/intership/newsletter-intership-proxym/backend/newsletter-application/src/main/resources/templates/validation_email_en.txt");
        }else if(subscriber.getLanguage().name().equals("FRENCH")){
            emailTemplate = getEmailTemplateFromFile("C:/intership/newsletter-intership-proxym/backend/newsletter-application/src/main/resources/templates/validation_email_fr.txt");
        }else{
            emailTemplate = getEmailTemplateFromFile("C:/intership/newsletter-intership-proxym/backend/newsletter-application/src/main/resources/templates/validation_email_ar.txt");
        }
        String firstName = subscriber.getFirstName();
        String lastName = subscriber.getLastName();
        String email = subscriber.getEmail();
        String language = subscriber.getLanguage().toString();

        String emailBody = emailTemplate
                .replace("$firstName", firstName)
                .replace("$lastName", lastName)
                .replace("$email", email)
                .replace("$language", language)
                .replace("$code", randomCode);


    }

    private String getEmailTemplateFromFile(String filePath) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }




    private String generateRandomCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

}