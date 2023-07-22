package com.proxym.newsletter.application.repository;

import com.proxym.newsletter.application.entity.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidationRepository extends JpaRepository<Validation, Long> {
          Validation findByEmailAndCode(String email, String code);
}
