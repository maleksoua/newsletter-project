package com.proxym.newsletter.application.repository;

import com.proxym.newsletter.application.entity.SubscriptionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRequestRepository extends JpaRepository<SubscriptionRequest, Long> {
    SubscriptionRequest findByEmailAndCode(String email, String code);
}
