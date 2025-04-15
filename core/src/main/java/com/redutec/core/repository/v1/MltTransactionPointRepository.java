package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.MltTransactionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltTransactionPointRepository extends JpaRepository<MltTransactionPoint, Integer>, JpaSpecificationExecutor<MltTransactionPoint> {
}