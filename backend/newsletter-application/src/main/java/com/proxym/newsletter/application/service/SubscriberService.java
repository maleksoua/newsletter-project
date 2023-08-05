package com.proxym.newsletter.application.service;

import com.proxym.newsletter.application.entity.Subscriber;

public interface SubscriberService {
    void validateSubscription(Subscriber subscriber);
    void requestSubscription(Subscriber subscriber);

}



