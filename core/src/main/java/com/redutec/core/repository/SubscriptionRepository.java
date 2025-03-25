package com.redutec.core.repository;

import com.redutec.core.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer>, JpaSpecificationExecutor<Subscription> {
}