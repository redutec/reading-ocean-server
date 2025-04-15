package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.SubscriptionPassItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionPassItemRepository extends JpaRepository<SubscriptionPassItem, Integer>, JpaSpecificationExecutor<SubscriptionPassItem> {
}