package com.proxym.newsletter.application.controller;

import com.proxym.newsletter.application.entity.Article;
import com.proxym.newsletter.application.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/article-resources")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    @DeleteMapping("/article/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/articles")
    public ResponseEntity<List<Article>> findAllArticles() {
        List<Article> articles = articleRepository.findAll();
        return ResponseEntity.ok(articles);
    }
    @PostMapping("/articles")
    public ResponseEntity<Article> saveArticle(@RequestBody Article article) {
        return ResponseEntity.ok(articleService.addArticle(article));
    }
}
