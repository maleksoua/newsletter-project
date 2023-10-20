package com.proxym.newsletter.application.entity;

import com.proxym.newsletter.application.enums.ArticleSendStatus;
import com.proxym.newsletter.application.enums.Language;
import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Article {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Subject subject;
    @Column(columnDefinition = "TEXT")
    private String information;
    private Language language;
    @Enumerated(EnumType.STRING)
    private ArticleSendStatus status;
    private String title;

}
