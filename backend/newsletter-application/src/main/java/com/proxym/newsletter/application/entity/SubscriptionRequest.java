package com.proxym.newsletter.application.entity;

import com.proxym.newsletter.application.enums.Language;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class SubscriptionRequest {
    @Id
    @GeneratedValue
    private Long id;
    private String code;
    @Column(unique = true)
    private String email;
    @NotBlank
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Language language;
    @ManyToMany(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(
            name = "subjects_like",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Set<Subject> subjects;

}
