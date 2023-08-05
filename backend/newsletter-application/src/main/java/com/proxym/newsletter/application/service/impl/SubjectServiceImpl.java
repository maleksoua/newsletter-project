package com.proxym.newsletter.application.service.impl;

import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.enums.Category;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.service.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    public Map<Category, List<Subject>> subjectByCategory() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream().collect(Collectors.groupingBy(Subject::getCategory));
    }
}
