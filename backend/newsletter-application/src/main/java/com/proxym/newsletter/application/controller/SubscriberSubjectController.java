package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.repository.SubscriberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriber/subject")
public class SubscriberSubjectController {

    private SubjectRepository subjectRepository;
    private SubscriberRepository subscriberRepository;

    @PostMapping
    public ResponseEntity<Subscriber> saveSubscriberWithSubject(@RequestBody Subscriber subscriber) {
        return ResponseEntity.ok(subscriberRepository.save(subscriber));

    }

    @GetMapping
    public ResponseEntity<List<Subscriber>> finAllSubscribers() {
        return ResponseEntity.ok(subscriberRepository.findAll());
    }

    @GetMapping("/{subscriberId}")
    public ResponseEntity<Subscriber> findSubscriber(@PathVariable Long subscriberId) {
        return ResponseEntity.ok(subscriberRepository.findById(subscriberId).orElse(null));

    }

    @GetMapping("/find/{email}")
    public ResponseEntity<List<Subscriber>> findSubscriberByEmail(@PathVariable String email) {
        return ResponseEntity.ok(subscriberRepository.findByNameContaining(email));

    }
}
