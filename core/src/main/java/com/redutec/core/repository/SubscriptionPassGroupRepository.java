package com.redutec.core.repository;

import com.redutec.core.entity.SubscriptionPassGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionPassGroupRepository extends JpaRepository<SubscriptionPassGroup, Integer>, JpaSpecificationExecutor<SubscriptionPassGroup> {
}