package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.entity.*;
import com.proxym.newsletter.application.enums.Category;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.sevice.EmailSenderService;

import com.proxym.newsletter.application.sevice.SubjectService;
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
    private final SubjectService subjectService;
    private final SubjectRepository subjectRepository;

    @GetMapping("/subjects")
    public ResponseEntity<List<Subject>> findAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return ResponseEntity.ok(subjects);
    }

    @GetMapping("/subjectsByCategory")
    public ResponseEntity<Map<Category, List<Subject>>> findAllSubjectsByCategory() throws MessagingException {

        return ResponseEntity.ok(subjectService.SubjectByCategory());
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

    @PostMapping("/subjects/send_email")

    public ResponseEntity<String> sendEmailToSubscribers(@RequestBody EmailRequest emailRequest) throws MessagingException {

        return ResponseEntity.ok(emailSenderService.sendEmailToSubscriber(emailRequest));
    }



}





