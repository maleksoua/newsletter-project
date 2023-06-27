package com.proxym.newsletter.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Subscriber {
    @Id
    @GeneratedValue
    private Long id;
    private String first_name;
    private String last_name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Language language;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "SUBSCRIBER_SUBJECT_TABLE", joinColumns = {@JoinColumn(name = "subscriber_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "subject_id", referencedColumnName = "id")
            })
    private Set<Subject> subjects;


}
