package com.proxym.newsletter.application.sevice;

import com.proxym.newsletter.application.entity.Article;

import jakarta.mail.MessagingException;

public interface ArticleService {
    Article addArticle(Article article)throws MessagingException;
}
