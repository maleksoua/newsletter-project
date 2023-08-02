package com.proxym.newsletter.application.sevice.impl;
import com.proxym.newsletter.application.entity.Language;
import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.entity.Validation;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.repository.SubscriberRepository;
import com.proxym.newsletter.application.repository.ValidationRepository;
import com.proxym.newsletter.application.sevice.EmailSenderService;
import com.proxym.newsletter.application.sevice.SubscriberService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
@Service
@AllArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final SubjectRepository subjectRepository;

    private final EmailSenderService emailSenderService;
    private final ValidationRepository validationRepository;

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
        Validation validationEntity = new Validation();
        validationEntity.setCode(randomCode);
        validationEntity.setEmail(subscriber.getEmail());

        String bodyString = subscriber.getEmail() + "|" +
                subscriber.getLastName() + "|" +
                subscriber.getFirstName() + "|" +
                subscriber.getLanguage() + "|" +
                subjectNames;
        validationEntity.setBody(bodyString);

        validationRepository.save(validationEntity);
        String emailBody="";

         if(subscriber.getLanguage().name().equals("ENGLISH")){
              emailBody = "<html><header></header><body><h3>Your First name is:</h3>" +
                subscriber.getFirstName() + "<h3>Your Last name is:</h3>" +
                subscriber.getLastName() + "<h3>Your email is:</h3>" +
                subscriber.getEmail() + "<h3>Your Language:</h3>" +
                subscriber.getLanguage() + "<h3>Your subjects are:</h3>" +
                subjectNames + "<br>  code " + randomCode + " </body></html>";
             emailSenderService.sendEmail(subscriber.getEmail(), "validation", emailBody);

          }

        if(subscriber.getLanguage().name().equals("FRENCH")){
              emailBody = "<html><header></header><body><h3> Votre prénom est:</h3>" +
                     subscriber.getFirstName() + "<h3>Votre nom est:</h3>" +
                     subscriber.getLastName() + "<h3>Votre email est:</h3>" +
                     subscriber.getEmail() + "<h3>Votre langue est:</h3>" +
                     subscriber.getLanguage() + "<h3>Votre sujects sont:</h3>" +
                     subjectNames + "<br>  code " + randomCode + " </body></html>";
            emailSenderService.sendEmail(subscriber.getEmail(), "validation", emailBody);
         }


        // Send the email

    }

    private String generateRandomCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

}







