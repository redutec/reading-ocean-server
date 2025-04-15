package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CouponHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Integer>, JpaSpecificationExecutor<CouponHistory> {
}