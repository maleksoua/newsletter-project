package com.proxym.newsletter.application.sevice;

import com.proxym.newsletter.application.entity.Category;
import com.proxym.newsletter.application.entity.Subject;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Map;

public interface SubjectService {
    Map<Category, List<Subject>> SubjectByCategory()throws MessagingException;
}
