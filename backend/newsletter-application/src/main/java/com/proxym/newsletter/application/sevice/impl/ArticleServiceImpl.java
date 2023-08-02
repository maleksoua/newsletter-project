package com.proxym.newsletter.application.sevice.impl;

import com.proxym.newsletter.application.entity.Article;
import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.repository.ArticleRepository;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.sevice.ArticleService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final SubjectRepository subjectRepository;


    @Override
    public Article addArticle(Article article) throws MessagingException {

        if (article.getSubject() != null) {
                Subject existingSubject = subjectRepository.findByName(article.getSubject().getName());
                if (existingSubject != null) {
                    article.setSubject(existingSubject);
                }
            }


        return articleRepository.save(article);
    }}