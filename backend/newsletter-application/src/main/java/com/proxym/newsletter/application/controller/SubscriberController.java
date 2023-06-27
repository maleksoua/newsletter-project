package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriber-resources")
@RequiredArgsConstructor
public class SubscriberController {
    private final SubscriberRepository subscriberRepository;

    @PostMapping("/add")
    public ResponseEntity<Subscriber> addSubscriber(@RequestBody Subscriber subscriber) {
        return ResponseEntity.ok(subscriberRepository.save(subscriber));

    }

    @GetMapping("/all")
    public ResponseEntity<List<Subscriber>> finAllSubscribers() {
        return ResponseEntity.ok(subscriberRepository.findAll());
    }

    @GetMapping("/find/{email}")
    public ResponseEntity<Subscriber> findSubscriberByEmail(@PathVariable String email) {
        return ResponseEntity.ok(subscriberRepository.findByEmail(email));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subscriberRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
