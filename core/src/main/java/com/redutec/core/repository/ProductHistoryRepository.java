package com.redutec.core.repository;

import com.redutec.core.entity.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductHistoryRepository extends JpaRepository<ProductHistory, Integer>, JpaSpecificationExecutor<ProductHistory> {
}