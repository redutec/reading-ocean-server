package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.SubscriptionOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionOrderRepository extends JpaRepository<SubscriptionOrder, Integer>, JpaSpecificationExecutor<SubscriptionOrder> {
}