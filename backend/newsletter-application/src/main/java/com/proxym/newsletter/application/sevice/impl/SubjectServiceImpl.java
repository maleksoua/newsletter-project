package com.proxym.newsletter.application.sevice.impl;

import com.proxym.newsletter.application.enums.Category;
import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.sevice.SubjectService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    public Map<Category, List<Subject>> SubjectByCategory() throws MessagingException {
        List<Subject> subjects = subjectRepository.findAll();
        Map<Category, List<Subject>> subjectMap = new HashMap<>();

        // Group subjects by category
        for (Subject subject : subjects) {
            Category category = subject.getCategory();
            if (!subjectMap.containsKey(category)) {
                subjectMap.put(category, new ArrayList<>());
            }
            subjectMap.get(category).add(subject);
        }

        return subjectMap;
    }
}
