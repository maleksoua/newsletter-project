package com.proxym.newsletter.application.sevice.impl;

import com.proxym.newsletter.application.entity.Language;
import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.entity.Validation;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.repository.SubscriberRepository;
import com.proxym.newsletter.application.repository.ValidationRepository;

import com.proxym.newsletter.application.sevice.ConfirmationService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class confirmationServiceImpl implements ConfirmationService {

    private final SubjectRepository subjectRepository;
    private final SubscriberRepository subscriberRepository;
    private final ValidationRepository validationRepository;

    @Override
    public String confirmationSubscriber(Validation confirmationRequest) throws MessagingException {
        String email = confirmationRequest.getEmail();
        String code = confirmationRequest.getCode();

        // Check if the code and email correspond to an existing validation
        Validation validationEntity = validationRepository.findByEmailAndCode(email, code);
        if (validationEntity == null) {
            // Validation not found or code doesn't match
            return "Invalid email or code";
        }

        String bodyString = validationEntity.getBody();
        String[] subscriberInfo = bodyString.split("\\|");

        if (subscriberInfo.length < 4) {
            // Insufficient information in the bodyString
            return "Invalid data format";
        }

        // Create a new Subscriber object to save the confirmed subscriber
        Subscriber confirmedSubscriber = new Subscriber();
        confirmedSubscriber.setEmail(subscriberInfo[0]);
        confirmedSubscriber.setLastName(subscriberInfo[1]);
        confirmedSubscriber.setFirstName(subscriberInfo[2]);

        String languageString = subscriberInfo[3];
        Language language;
        switch (languageString) {
            case "ENGLISH":
                language = Language.ENGLISH;
                break;
            case "FRENCH":
                language = Language.FRENCH;
                break;
            default:
                // Invalid language
                return "Invalid language: " + languageString;
        }
        confirmedSubscriber.setLanguage(language);

        if (subscriberInfo.length >= 5 && !subscriberInfo[4].isEmpty()) {
            // Extract subjects from the string and add them to the Subscriber
            String subjectsString = subscriberInfo[4];
            Set<Subject> subjects = new HashSet<>();
            subjectsString = subjectsString.substring(0, subjectsString.length()); // Remove the square brackets
            String[] subjectNames = subjectsString.split(",");

            for (String subjectName : subjectNames) {
                Subject subject = subjectRepository.findByName(subjectName);
                if (subject != null) {
                    subjects.add(subject);
                }
            }
            confirmedSubscriber.setSubjects(subjects);
        }

        subscriberRepository.save(confirmedSubscriber);
        validationRepository.delete(validationEntity);

        return "Subscriber added successfully!";
    }
}