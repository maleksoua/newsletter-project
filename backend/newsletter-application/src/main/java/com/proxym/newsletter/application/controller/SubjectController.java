package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.entity.EmailMessage;
import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.repository.SubscriberRepository;
import com.proxym.newsletter.application.sevice.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("subject-resources")
@RequiredArgsConstructor
public class SubjectController {
    private final EmailSenderService emailSenderService;
    private final SubscriberRepository subscriberRepository;
    private final SubjectRepository subjectRepository;

    @GetMapping("/subjects")
    public ResponseEntity<List<Subject>> findAll() {
        List<Subject> subjects = subjectRepository.findAll();
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<Subject> findById(@PathVariable Long id) throws Exception {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new Exception("Subject does not exist"));
        return ResponseEntity.ok(subject);
    }

    @PostMapping("/Add")
    public ResponseEntity<Subject> saveSubject(@RequestBody Subject subject) {
        Subject savedSubject = subjectRepository.save(subject);
        return ResponseEntity.ok(savedSubject);
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/subjects/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody Subject updatedSubject) throws Exception {
        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new Exception("Subject does not exist"));

        existingSubject.setName(updatedSubject.getName());
        existingSubject.setCategory(updatedSubject.getCategory());

        Subject savedSubject = subjectRepository.save(existingSubject);
        return ResponseEntity.ok(savedSubject);
    }
    @PostMapping("/send_email/{subjectId}")
    public ResponseEntity<String> sendEmailToSubscribers(@PathVariable Long subjectId) {
        // Récupérer le sujet depuis le référentiel
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        // Récupérer la liste des abonnés du sujet
        Set<Subscriber> subscribers = subject.getSubscribers();

        // Vérifier s'il y a des abonnés
        if (subscribers.isEmpty()) {
            return ResponseEntity.ok("No subscribers found");
        }

        // Envoyer l'e-mail à tous les abonnés
        for (Subscriber subscriber : subscribers) {
            emailSenderService.sendEmail(subscriber.getEmail(), subject.getName(), subject.getCategory().toString());
        }

        return ResponseEntity.ok("E-mails sent to subscribers");
    }



}
