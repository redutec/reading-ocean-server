package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.SubscriptionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionProductRepository extends JpaRepository<SubscriptionProduct, Integer>, JpaSpecificationExecutor<SubscriptionProduct> {
}