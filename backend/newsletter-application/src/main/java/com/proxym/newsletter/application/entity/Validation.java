package com.proxym.newsletter.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Validation {
    @Id
    @GeneratedValue
    private Long id;
    private String code;
    private String email;

    private String body;


}
