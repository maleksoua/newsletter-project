package com.proxym.newsletter.application.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Subject {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String name;
    @Enumerated(EnumType.STRING)

    private Category category;
    @JsonIgnore

    @ManyToMany(mappedBy = "subjects")
    Set<Subscriber> subscribers;
    public Subject(String name, Category category) {
        this.name = name;
        this.category = category;
    }

}