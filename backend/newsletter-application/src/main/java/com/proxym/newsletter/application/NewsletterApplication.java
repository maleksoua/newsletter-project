package com.proxym.newsletter.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NewsletterApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewsletterApplication.class, args);
    }


} 