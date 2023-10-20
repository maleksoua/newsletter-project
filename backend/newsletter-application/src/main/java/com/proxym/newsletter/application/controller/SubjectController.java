package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.enums.Category;
import com.proxym.newsletter.application.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("subject-resources")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping("/subjects")
    public ResponseEntity<List<Subject>> findAllSubjects() {
        return ResponseEntity.ok(subjectService.findAll());
    }

    @GetMapping("/subjectsByCategory")
    public ResponseEntity<Map<Category, List<Subject>>> findAllSubjectsByCategory() {
        return ResponseEntity.ok(subjectService.subjectByCategory());
    }

    @PostMapping("/subjects")
    public ResponseEntity<Void> saveSubject(@RequestBody Subject subject) {
        subjectService.add(subject);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subjects/{id}")
    public Subject getById(@PathVariable Long id) {
        return subjectService.getById(id);
    }

    @PutMapping("/subjects/{id}")
    public ResponseEntity<Void> updateSubject(@PathVariable Long id, @RequestBody Subject updatedSubject) {
        subjectService.update(id, updatedSubject);
        return ResponseEntity.noContent().build();
    }


}





