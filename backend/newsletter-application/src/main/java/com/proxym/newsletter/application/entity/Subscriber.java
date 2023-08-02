package com.proxym.newsletter.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Subscriber {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Language language;

    @ManyToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(
            name = "subjects_like",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    Set<Subject> subjects;


    /*public void addSubject(Subject existingSubject) {
    }*/


   /* public Subscriber( String firstName, String lastName, String email, Language language, Set<Subject> subjects) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.language = language;
        this.subjects = subjects;
    }
    public Set<String> getSubjectNames() {
        Set<String> subjectNames = new HashSet<>();
        for (Subject subject : subjects) {
            subjectNames.add(subject.getName());
        }
        return subjectNames;
    }*/


}

