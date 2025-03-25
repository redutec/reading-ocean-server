package com.redutec.core.repository;

import com.redutec.core.entity.SubscriptionPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionPassRepository extends JpaRepository<SubscriptionPass, Integer>, JpaSpecificationExecutor<SubscriptionPass> {
}