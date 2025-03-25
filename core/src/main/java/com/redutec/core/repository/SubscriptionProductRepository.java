package com.redutec.core.repository;

import com.redutec.core.entity.SubscriptionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionProductRepository extends JpaRepository<SubscriptionProduct, Integer>, JpaSpecificationExecutor<SubscriptionProduct> {
}