package com.redutec.core.repository;

import com.redutec.core.entity.SubscriptionPassItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionPassItemRepository extends JpaRepository<SubscriptionPassItem, Integer>, JpaSpecificationExecutor<SubscriptionPassItem> {
}