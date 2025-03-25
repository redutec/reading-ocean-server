package com.redutec.core.repository;

import com.redutec.core.entity.MltSubscriptionAcademyProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltSubscriptionAcademyProductRepository extends JpaRepository<MltSubscriptionAcademyProduct, Integer>, JpaSpecificationExecutor<MltSubscriptionAcademyProduct> {
}