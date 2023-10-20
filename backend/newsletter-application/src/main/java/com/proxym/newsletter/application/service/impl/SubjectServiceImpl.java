package com.proxym.newsletter.application.service.impl;

import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.enums.Category;
import com.proxym.newsletter.application.repository.SubjectRepository;
import com.proxym.newsletter.application.service.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    @Override
    public Map<Category, List<Subject>> subjectByCategory() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream().collect(Collectors.groupingBy(Subject::getCategory));
    }

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public void add(Subject subject) {
        subjectRepository.save(subject);
    }

    @Override
    public void delete(Long id) {
        subjectRepository.deleteById(id);
    }
    @Override
    public Subject getById(Long id) {
        return subjectRepository.findById(id).get();
    }

    @Override
    public void update(Long id, Subject updatedSubject) {
        Optional<Subject> optionalSubject = subjectRepository.findById(id);
        if (optionalSubject.isEmpty()) {
            throw new IllegalArgumentException("subject not found");
        }
        Subject existingSubject = optionalSubject.get();

        existingSubject.setName(updatedSubject.getName());
        existingSubject.setCategory(updatedSubject.getCategory());

        subjectRepository.save(existingSubject);
    }

}
