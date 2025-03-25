package com.redutec.core.repository;

import com.redutec.core.entity.SubscriptionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionOrderRepository extends JpaRepository<SubscriptionOrder, Integer>, JpaSpecificationExecutor<SubscriptionOrder> {
}