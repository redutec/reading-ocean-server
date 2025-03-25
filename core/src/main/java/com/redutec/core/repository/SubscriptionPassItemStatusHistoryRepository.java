package com.redutec.core.repository;

import com.redutec.core.entity.SubscriptionPassItemStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionPassItemStatusHistoryRepository extends JpaRepository<SubscriptionPassItemStatusHistory, Integer>, JpaSpecificationExecutor<SubscriptionPassItemStatusHistory> {
}