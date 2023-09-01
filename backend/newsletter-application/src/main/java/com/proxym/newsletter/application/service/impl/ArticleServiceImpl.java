package com.proxym.newsletter.application.service.impl;

import com.proxym.newsletter.application.entity.Article;
import com.proxym.newsletter.application.repository.ArticleRepository;
import com.proxym.newsletter.application.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public void add(Article article) {
        articleRepository.save(article);
    }
    @Override
    public Article getById(Long id) {
        return articleRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }
    @Override
    public void update(Long id, Article updatedArticle) {
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if (optionalArticle.isEmpty()) {
            throw new IllegalArgumentException("article not found");
        }
        Article existingArticle = optionalArticle.get();

        existingArticle.setTitle(updatedArticle.getTitle());
        existingArticle.setStatus(updatedArticle.getStatus());
        existingArticle.setInformation(updatedArticle.getInformation());
        existingArticle.setLanguage(updatedArticle.getLanguage());
        existingArticle.setSubject(updatedArticle.getSubject());
        articleRepository.save(existingArticle);
    }

}