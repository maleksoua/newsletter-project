package com.proxym.newsletter.application.bootstrap;

import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.enums.Category;
import com.proxym.newsletter.application.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapDatabase implements CommandLineRunner {
    private final SubjectRepository subjectRepository;

    @Override
    public void run(String... args) {
        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(new Subject("Big Data", Category.TECHNOLOGY));
        subjectList.add(new Subject("Artificial intelligence", Category.TECHNOLOGY));
        subjectList.add(new Subject("Cancer", Category.HEALTH));

        subjectRepository.saveAll(subjectList);
    }
}



