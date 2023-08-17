package com.proxym.newsletter.application.entity;

import com.proxym.newsletter.application.enums.Language;
import com.proxym.newsletter.application.enums.SubscriptionRequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
    private Integer code;
    @Column(unique = true)
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Language language;
    @Enumerated(EnumType.STRING)
    private SubscriptionRequestStatus status;

    @ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "subjects_like",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    @NotEmpty
    private Set<Subject> subjects;

}
