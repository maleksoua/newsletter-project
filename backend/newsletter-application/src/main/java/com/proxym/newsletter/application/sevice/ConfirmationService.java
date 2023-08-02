package com.proxym.newsletter.application.sevice;

import com.proxym.newsletter.application.entity.Validation;
import jakarta.mail.MessagingException;

public interface ConfirmationService {

    String confirmationSubscriber(Validation confirmationRequest)throws MessagingException;
}
