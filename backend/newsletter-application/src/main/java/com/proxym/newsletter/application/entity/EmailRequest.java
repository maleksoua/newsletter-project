package com.proxym.newsletter.application.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class EmailRequest {

    @Id
    @GeneratedValue
    private Long subjectId;
    private String language;
    private String message;
    private String subjectEmail;
  //  private String email;
}

