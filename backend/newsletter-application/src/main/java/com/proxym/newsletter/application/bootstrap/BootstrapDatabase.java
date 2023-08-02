package com.proxym.newsletter.application.bootstrap;

import com.proxym.newsletter.application.entity.Category;
import com.proxym.newsletter.application.entity.Language;
import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.entity.Subscriber;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class BootstrapDatabase implements CommandLineRunner {
    private final SubjectRepository subjectRepository;
    //private final SubscriberRepository subscriberRepository;



        @Override
        public void run(String... args) {
            List<Subject> subjectList = new ArrayList<>();
            Subject subject1 = new Subject("Big Data", Category.TECHNOLOGIES);
            Subject subject2 = new Subject("Artificial intelligence", Category.TECHNOLOGIES);
            Subject subject3 = new Subject("Cancer", Category.HEALTH);
            subjectList.add(subject1);
            subjectList.add(subject2);
            subjectList.add(subject3);

            subjectRepository.saveAll(subjectList);
        }
    }



