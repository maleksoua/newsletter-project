package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.entity.SubscriptionRequest;
import com.proxym.newsletter.application.enums.SubscriptionRequestStatus;
import com.proxym.newsletter.application.service.SubscriberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriber-resources")
@RequiredArgsConstructor
public class SubscriberController {

    private final SubscriberService subscriberService;

    @PostMapping("/add")
    public SubscriptionRequest addSubscriber(@RequestBody SubscriptionRequest subscriber) {
        subscriber.setStatus(SubscriptionRequestStatus.INITIATED);
        return subscriberService.requestSubscription(subscriber);

    }


    @GetMapping("/all")
    public ResponseEntity<List<Subscriber>> finAllSubscribers() {
        return ResponseEntity.ok(subscriberService.findAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSubscriber(@PathVariable Long id) {
        subscriberService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/subscriber/{id}")
    public ResponseEntity<Void> updateSubscriber(@PathVariable Long id, @RequestBody Subscriber updatedSubscriber) {
        subscriberService.update(id, updatedSubscriber);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/confirmation/{email}")
    public ResponseEntity<String> validateSubscriber(@PathVariable String email, @RequestBody SubscriptionRequest subscriptionRequest) {
        return subscriberService.validateSubscription(email, subscriptionRequest);


    }
}

