package com.redutec.core.repository;

import com.redutec.core.entity.MltSubscriptionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltSubscriptionProductRepository extends JpaRepository<MltSubscriptionProduct, Integer>, JpaSpecificationExecutor<MltSubscriptionProduct> {
}