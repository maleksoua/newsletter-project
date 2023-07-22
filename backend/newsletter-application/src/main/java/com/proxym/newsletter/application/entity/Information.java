package com.proxym.newsletter.application.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Information {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Subject subject;
    private String paragraph;


}
