package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.enums.Category;
import com.proxym.newsletter.application.enums.Language;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class UtilsController {

    @GetMapping("/language")
    public ResponseEntity<List<Language>> findAllLanguages() {
        return ResponseEntity.ok(List.of(Language.values()));
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(List.of(Category.values()));
    }
}
