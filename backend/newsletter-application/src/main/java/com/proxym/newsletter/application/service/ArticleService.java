package com.proxym.newsletter.application.service;

import com.proxym.newsletter.application.entity.Article;

import java.util.List;


public interface ArticleService {
    void add(Article article);
    List<Article> findAll();
    void delete(Long id);
    void update(Long id,Article updatedArticle);
    Article getById(Long id);
}
