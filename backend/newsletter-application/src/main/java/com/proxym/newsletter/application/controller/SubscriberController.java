package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/subscriber-resources")
@RequiredArgsConstructor
public class SubscriberController {

    private final SubscriberRepository subscriberRepository;
    private final SubjectRepository subjectRepository;

    @PostMapping("/add")
    public ResponseEntity<Subscriber> addSubscriber(@RequestBody Subscriber subscriber) {
        if (subscriber.getSubjects() != null) {
            Set<Subject> attachedSubjects = new HashSet<>();
            for (Subject subject : subscriber.getSubjects()) {
                Subject existingSubject = subjectRepository.findByName(subject.getName());
                if (existingSubject != null) {
                    attachedSubjects.add(existingSubject);
                }
            }
            subscriber.setSubjects(attachedSubjects);
        }
        return ResponseEntity.ok(subscriberRepository.save(subscriber));
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
}
