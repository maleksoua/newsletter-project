package com.proxym.newsletter.application.service;

import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.entity.SubscriptionRequest;

import java.util.List;

public interface SubscriberService {
    void validateSubscription(String email,SubscriptionRequest subscriptionRequest);

    SubscriptionRequest requestSubscription(SubscriptionRequest subscriber);

    void update(Long id, Subscriber updatedSubscriber);

    List<Subscriber> findAll();

    void delete(Long id);

}



