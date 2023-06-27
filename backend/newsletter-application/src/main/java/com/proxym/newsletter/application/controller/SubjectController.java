package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.entity.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing subjects in the newsletter application
 */
@RestController
@RequestMapping("subject-resources")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectRepository subjectRepository;

    /**
     * Retrieve all subjects
     */
    @GetMapping("/subjects")
    public ResponseEntity<List<Subject>> findAll() {
        List<Subject> subjects = subjectRepository.findAll();
        return ResponseEntity.ok(subjects);
    }

    /**
     * Find a subject by ID
     */
    @GetMapping("/subjects/{id}")
    public ResponseEntity<Subject> findById(@PathVariable Long id) throws Exception {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new Exception("Subject does not exist"));
        return ResponseEntity.ok(subject);
    }

    /**
     * Create a new subject
     */
    @PostMapping("/subjects")
    public ResponseEntity<Subject> saveSubject(@RequestBody Subject subject) {
        Subject savedSubject = subjectRepository.save(subject);
        return ResponseEntity.ok(savedSubject);
    }

    /**
     * Delete a subject by ID
     */
    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}