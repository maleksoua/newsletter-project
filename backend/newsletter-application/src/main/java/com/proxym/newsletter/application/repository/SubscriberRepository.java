package com.proxym.newsletter.application.repository;

import com.proxym.newsletter.application.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    List<Subscriber> findByNameContaining(String email);
}
