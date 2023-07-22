package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.entity.Category;
import com.proxym.newsletter.application.entity.EmailMessage;
import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.sevice.EmailSenderService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("subject-resources")
@RequiredArgsConstructor
public class SubjectController {
    private final EmailSenderService emailSenderService;
    private final SubjectRepository subjectRepository;

    @GetMapping("/subjects")
    public ResponseEntity<List<Subject>> findAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/subjectsByCategory")
    public ResponseEntity<Map<Category, List<Subject>>> findAllSubjectsByCategory() {
        List<Subject> subjects = subjectRepository.findAll();
        Map<Category, List<Subject>> subjectMap = new HashMap<>();

        // Group subjects by category
        for (Subject subject : subjects) {
            Category category = subject.getCategory();
            if (!subjectMap.containsKey(category)) {
                subjectMap.put(category, new ArrayList<>());
            }
            subjectMap.get(category).add(subject);
        }

        return ResponseEntity.ok(subjectMap);
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<Subject> findSubjectById(@PathVariable Long id) throws Exception {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new Exception("Subject does not exist"));
        return ResponseEntity.ok(subject);
    }

    @PostMapping("/subjects")
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

    @PostMapping("/subjects/{subjectId}/send_email")
    public ResponseEntity<String> sendEmailToSubscribers(@PathVariable Long subjectId, @RequestBody EmailMessage emailMessage) throws MessagingException {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Set<Subscriber> subscribers = subject.getSubscribers();

        if (subscribers.isEmpty()) {
            return ResponseEntity.ok("No subscribers found");
        }

        for (Subscriber subscriber : subscribers) {
            emailSenderService.sendEmail(subscriber.getEmail(), emailMessage.getSubject(), emailMessage.getMessage() + subject.getName() + subject.getCategory());
        }

        return ResponseEntity.ok("E-mails sent to subscribers");
    }

    /*@GetMapping("/subjects/category/{category}")
    public ResponseEntity<List<Subject>> findSubjectsByCategory(@PathVariable Category category) {
        List<Subject> subjects = subjectRepository.findByCategory(category);
        return ResponseEntity.ok(subjects);
    }*/
}
