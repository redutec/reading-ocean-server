package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.MltSubscriptionProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltSubscriptionProductRepository extends JpaRepository<MltSubscriptionProduct, Integer>, JpaSpecificationExecutor<MltSubscriptionProduct> {
}