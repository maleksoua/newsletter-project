package com.proxym.newsletter.application.repository;

import com.proxym.newsletter.application.entity.SubscriptionRequest;
import com.proxym.newsletter.application.enums.SubscriptionRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRequestRepository extends JpaRepository<SubscriptionRequest, Long> {
    List<SubscriptionRequest> findByStatus(SubscriptionRequestStatus status);

    SubscriptionRequest findByCodeAndEmail(Integer code, String email);
}
