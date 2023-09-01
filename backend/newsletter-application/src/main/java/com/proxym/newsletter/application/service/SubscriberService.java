package com.proxym.newsletter.application.service;

import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.entity.SubscriptionRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubscriberService {
    ResponseEntity<String> validateSubscription(String email, SubscriptionRequest subscriptionRequest);

    SubscriptionRequest requestSubscription(SubscriptionRequest subscriber);

    void update(Long id, Subscriber updatedSubscriber);

    List<Subscriber> findAll();


     void deleteSubscription(Long id);

}



