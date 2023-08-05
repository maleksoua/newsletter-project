package com.proxym.newsletter.application.service;

import com.proxym.newsletter.application.enums.Category;
import com.proxym.newsletter.application.entity.Subject;

import java.util.List;
import java.util.Map;

public interface SubjectService {
    Map<Category, List<Subject>> subjectByCategory();
}
