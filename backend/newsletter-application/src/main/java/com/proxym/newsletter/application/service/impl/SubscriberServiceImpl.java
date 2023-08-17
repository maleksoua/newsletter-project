package com.proxym.newsletter.application.service.impl;

import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.entity.SubscriptionRequest;
import com.proxym.newsletter.application.enums.SubscriptionRequestStatus;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.repository.SubscriberRepository;
import com.proxym.newsletter.application.repository.SubscriptionRequestRepository;
import com.proxym.newsletter.application.service.SubscriberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {
   private  final SubscriptionRequestRepository subscriptionRequestRepository;
    private final SubjectRepository subjectRepository;
    private final SubscriberRepository subscriberRepository;


    @Override
    public void update(Long id, Subscriber updatedSubscriber) {
        Optional<Subscriber> optionalSubscriber = subscriberRepository.findById(id);
        if (optionalSubscriber.isEmpty()) {
            throw new IllegalArgumentException("Subscriber not found");
        }
        Subscriber existingSubscriber = optionalSubscriber.get();
        existingSubscriber.setEmail(updatedSubscriber.getEmail());
        existingSubscriber.setFirstName(updatedSubscriber.getFirstName());
        existingSubscriber.setLastName(updatedSubscriber.getLastName());
        existingSubscriber.setLanguage(updatedSubscriber.getLanguage());
        existingSubscriber.setSubjects(updatedSubscriber.getSubjects());

        subscriberRepository.save(existingSubscriber);
    }

    @Override
    public List<Subscriber> findAll() {
        return subscriberRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        subscriberRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void validateSubscription(String email, SubscriptionRequest subscriptionRequest) {
        SubscriptionRequest subscriptionRequestConfirme = subscriptionRequestRepository.findByCodeAndEmail(subscriptionRequest.getCode(), email);

        if (subscriptionRequestConfirme != null) {
            subscriptionRequestConfirme.setStatus(SubscriptionRequestStatus.CONFIRMED);

            Subscriber subscriber = new Subscriber();
            subscriber.setEmail(subscriptionRequestConfirme.getEmail());
            subscriber.setFirstName(subscriptionRequestConfirme.getFirstName());
            subscriber.setLastName(subscriptionRequestConfirme.getLastName());
            subscriber.setLanguage(subscriptionRequestConfirme.getLanguage());
            if (subscriptionRequestConfirme.getSubjects() != null) {
                Set<Subject> attachedSubjects = new HashSet<>();
                for (Subject subject : subscriptionRequestConfirme.getSubjects()) {
                    Subject existingSubject = subjectRepository.findByName(subject.getName());
                    if (existingSubject != null) {
                        attachedSubjects.add(existingSubject);
                    }
                }
                subscriber.setSubjects(attachedSubjects);
            }

            subscriberRepository.save(subscriber);
            subscriptionRequestRepository.save(subscriptionRequestConfirme);
        }
    }
 public SubscriptionRequest requestSubscription(SubscriptionRequest subscriptionRequest) {

         if (subscriptionRequest.getSubjects() != null) {
            Set<Subject> attachedSubjects = new HashSet<>();
            for (Subject subject : subscriptionRequest.getSubjects()) {
                Subject existingSubject = subjectRepository.findByName(subject.getName());
                if (existingSubject != null) {
                    attachedSubjects.add(existingSubject);
                }
            }
            subscriptionRequest.setSubjects(attachedSubjects);
        }
        subscriptionRequestRepository.save(subscriptionRequest);
        return subscriptionRequest;
    }



}
