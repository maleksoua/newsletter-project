package com.proxym.newsletter.application.service;

import com.proxym.newsletter.application.entity.Subject;
import com.proxym.newsletter.application.enums.Category;

import java.util.List;
import java.util.Map;

public interface SubjectService {
    Map<Category, List<Subject>> subjectByCategory();

    List<Subject> findAll();

    void add(Subject subject);

    void delete(Long id);

    Subject getById(Long id);

    void update(Long id, Subject updatedSubject);

}
