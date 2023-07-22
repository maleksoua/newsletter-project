package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/category")
public class CategoryController {

    @GetMapping
    public ResponseEntity<Category[]> getCategories() {
        Category[] categories = Category.values();
        return ResponseEntity.ok(categories);
    }
}
