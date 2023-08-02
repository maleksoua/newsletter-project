package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.entity.Category;
import com.proxym.newsletter.application.entity.Language;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/Language")
public class LangugeController {
   @GetMapping
    public ResponseEntity<List<Language>> getLanguages() {
        return ResponseEntity.ok(List.of(Language.values()));
    }
}
