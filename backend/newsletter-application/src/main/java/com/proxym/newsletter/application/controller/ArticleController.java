package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.entity.Article;
import com.proxym.newsletter.application.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article-resources")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @DeleteMapping("/article/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/articles")
    public ResponseEntity<List<Article>> findAllArticles() {
        return ResponseEntity.ok(articleService.findAll());
    }

    @PostMapping("/articles")
    public ResponseEntity<Void> saveArticle(@RequestBody Article article) {

        articleService.add(article);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<Void> updateArticle(@PathVariable Long id, @RequestBody Article updatedArticle) {
        articleService.update(id, updatedArticle);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/articles/{id}")
    public Article getById(@PathVariable Long id) {
        return articleService.getById(id);
    }
}
