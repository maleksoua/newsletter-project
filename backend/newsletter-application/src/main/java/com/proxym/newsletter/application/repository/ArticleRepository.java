package com.proxym.newsletter.application.repository;

import com.proxym.newsletter.application.entity.Article;
import com.proxym.newsletter.application.enums.ArticleSendStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByStatus(ArticleSendStatus status);

}
