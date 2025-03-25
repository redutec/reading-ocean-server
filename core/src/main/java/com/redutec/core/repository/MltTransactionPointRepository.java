package com.redutec.core.repository;

import com.redutec.core.entity.MltTransactionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltTransactionPointRepository extends JpaRepository<MltTransactionPoint, Integer>, JpaSpecificationExecutor<MltTransactionPoint> {
}