package com.proxym.newsletter.application.service.impl;

import com.proxym.newsletter.application.entity.Article;
import com.proxym.newsletter.application.repository.ArticleRepository;
import com.proxym.newsletter.application.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public Article addArticle(Article article){
        return articleRepository.save(article);
    }
}