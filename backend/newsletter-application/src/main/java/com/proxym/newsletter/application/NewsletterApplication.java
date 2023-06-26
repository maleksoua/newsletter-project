package com.proxym.newsletter.application;

import entity.Subject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import repository.SubjectRepository;

@SpringBootApplication
public class NewsletterApplication {

	public static void main(String[] args) {

		SpringApplication.run(NewsletterApplication.class, args);
	@Bean
	CommandLineRunner initDatabaseH2(SubjectRepository subjectRepository){
		return args->{
			subjectRepository.save(new Subject(Long.perseLong("1"),"malek"));
			subjectRepository.save(new Subject(Long.perseLong("2"),"nidhal"));
			subjectRepository.save(new Subject(Long.perseLong("3"),"nodhnodh"));
		};
	}

		}




