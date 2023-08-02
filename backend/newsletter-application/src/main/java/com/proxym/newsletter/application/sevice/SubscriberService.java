package com.proxym.newsletter.application.sevice;

import com.proxym.newsletter.application.entity.Subscriber;
import jakarta.mail.MessagingException;

public interface SubscriberService {
    void validateSubscriber(Subscriber subscriber)throws MessagingException;
    Subscriber addSubscriber(Subscriber subscriber)throws MessagingException;;
}

