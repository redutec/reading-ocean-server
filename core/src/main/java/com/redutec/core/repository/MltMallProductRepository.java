package com.redutec.core.repository;

import com.redutec.core.entity.MltMallProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltMallProductRepository extends JpaRepository<MltMallProduct, Integer>, JpaSpecificationExecutor<MltMallProduct> {
}