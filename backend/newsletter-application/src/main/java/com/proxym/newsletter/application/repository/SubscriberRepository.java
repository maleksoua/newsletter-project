package com.proxym.newsletter.application.repository;

import com.proxym.newsletter.application.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Subscriber findByEmail(String email);

}
