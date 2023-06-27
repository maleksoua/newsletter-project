package com.proxym.newsletter.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Subject {
     @Id
     @GeneratedValue
     private Long id;
     private String name;
     @Enumerated(EnumType.STRING)
     private Category category;

     public Subject(String name, Category category) {
          this.name = name;
          this.category = category;
     }

}