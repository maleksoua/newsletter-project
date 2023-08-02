package com.proxym.newsletter.application.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Article {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Subject subject;
    private String information;
    private Language language;


}
