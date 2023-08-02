package com.proxym.newsletter.application.repository;

import com.proxym.newsletter.application.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
