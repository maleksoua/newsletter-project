package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.entity.SubscriptionRequest;
import com.proxym.newsletter.application.repository.SubscriberRepository;
import com.proxym.newsletter.application.service.SubscriberService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/subscriber-resources")
@RequiredArgsConstructor

public class SubscriberController {

    private final SubscriberRepository subscriberRepository;
    private final SubscriberService subscriberService;
    private final ConfirmationService confirmationService;


    @PostMapping("/add")
    public ResponseEntity<Subscriber> addSubscriber(@RequestBody Subscriber subscriber) throws MessagingException {
      return ResponseEntity.ok(subscriberService.addSubscriber(subscriber));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Subscriber>> finAllSubscribers() {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        return ResponseEntity.ok(subscribers);
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<Subscriber> findSubscriberByEmail(@PathVariable String email) {
        return ResponseEntity.ok(subscriberRepository.findByEmail(email));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSubscriber(@PathVariable Long id) {
        subscriberRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/subscriber/{id}")
    public ResponseEntity<Subscriber> updateSubscriber(@PathVariable Long id, @RequestBody Subscriber updatedSubscriber) throws Exception {
        Subscriber existingSubscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new Exception("Subscriber does not exist"));

        existingSubscriber.setEmail(updatedSubscriber.getEmail());
        existingSubscriber.setFirstName(updatedSubscriber.getFirstName());
        existingSubscriber.setLastName(updatedSubscriber.getLastName());
        existingSubscriber.setLanguage(updatedSubscriber.getLanguage());
        existingSubscriber.setSubjects(updatedSubscriber.getSubjects());

        Subscriber savedSubscriber = subscriberRepository.save(existingSubscriber);
        return ResponseEntity.ok(savedSubscriber);
    }
    @PostMapping("/validation")
    public ResponseEntity<Void> validateSubscriber(@RequestBody Subscriber subscriber) throws MessagingException {
       subscriberService.validateSubscriber(subscriber);
        return ResponseEntity.ok().build();}

    @PostMapping("/confirmation/{email}")
    public ResponseEntity<String> addConfirmedSubscriber(@PathVariable String email,@RequestBody SubscriptionRequest confirmationRequest) throws MessagingException {
        confirmationRequest.setEmail(email);
       String confirmation =confirmationService.confirmSubscriber(confirmationRequest);
        return ResponseEntity.ok(confirmation);
    }

}



