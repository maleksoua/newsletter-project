package com.proxym.newsletter.application.repository;
import com.proxym.newsletter.application.entity.Category;
import com.proxym.newsletter.application.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findByName(String name);
     List<Subject> findByCategory(Category category);
}

