package com.proxym.newsletter.application.service.impl;

import com.proxym.newsletter.application.entity.Article;
import com.proxym.newsletter.application.repository.ArticleRepository;
import com.proxym.newsletter.application.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public void add(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }


}