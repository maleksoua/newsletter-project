package com.proxym.newsletter.application.cron;

import com.proxym.newsletter.application.entity.SubscriptionRequest;
import com.proxym.newsletter.application.enums.SubscriptionRequestStatus;
import com.proxym.newsletter.application.repository.SubscriptionRequestRepository;
import com.proxym.newsletter.application.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Random;


@Component
public class ConfirmationRequestCron {
    private final SubscriptionRequestRepository subscriptionRequestRepository;
    private final EmailSenderService emailSenderService;
    private final Random random = new Random();

    private final String englishTemplate;
    private final String frenchTemplate;
    private final String arabicTemplate;
    @Value("${application.one.time.password.length}")
    private int optLength;

    @Value("${application.one.time.password.fixe}")
    private int fixedDelayValue;

    public ConfirmationRequestCron(SubscriptionRequestRepository subscriptionRequestRepository, EmailSenderService emailSenderService) throws IOException {
        this.subscriptionRequestRepository = subscriptionRequestRepository;
        this.emailSenderService = emailSenderService;

        Resource englishTemplateResource = new ClassPathResource("templates/validation_email_en.txt");
        englishTemplate = new String(englishTemplateResource.getInputStream().readAllBytes());
        Resource frenchTemplateResource = new ClassPathResource("templates/validation_email_fr.txt");
        frenchTemplate = new String(frenchTemplateResource.getInputStream().readAllBytes());
        Resource arabicTemplateResource = new ClassPathResource("templates/validation_email_ar.txt");
        arabicTemplate = new String(arabicTemplateResource.getInputStream().readAllBytes());
    }

    @Scheduled(fixedDelay = 5000)
    public void sendConfirmationRequest() {
        int maxRange = (int) Math.pow(10, optLength);
        List<SubscriptionRequest> subscriptionRequestList = subscriptionRequestRepository.findByStatus(SubscriptionRequestStatus.INITIATED);
        for (SubscriptionRequest subscriptionRequest: subscriptionRequestList) {
            final String subject = "not yet implemented";

            String body;
            switch (subscriptionRequest.getLanguage()){
                case ENGLISH -> body = englishTemplate;
                case FRENCH -> body = frenchTemplate;
                default -> body = arabicTemplate;
            }
            int randomCode = random.nextInt(maxRange);
            subscriptionRequest.setCode(randomCode);

            String formattedBody = body.replace("$code", String.valueOf(randomCode))
                    .replace("$firstName", subscriptionRequest.getFirstName())
                    .replace("$lastName", subscriptionRequest.getLastName())
                    .replace("$email", subscriptionRequest.getEmail())
                    .replace("$language", subscriptionRequest.getLanguage().toString());

            emailSenderService.sendEmail(subscriptionRequest.getEmail(), subject, formattedBody);
            emailSenderService.sendEmail("maleksoua123@yopmail.com", subject, formattedBody);
            subscriptionRequest.setStatus(SubscriptionRequestStatus.PENDING);
        }
        subscriptionRequestRepository.saveAll(subscriptionRequestList);
    }
}