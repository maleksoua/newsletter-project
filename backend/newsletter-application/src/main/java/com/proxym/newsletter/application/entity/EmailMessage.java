package com.proxym.newsletter.application.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
public class EmailMessage {
    private String to;
    private String subject;
    private String message;


}
