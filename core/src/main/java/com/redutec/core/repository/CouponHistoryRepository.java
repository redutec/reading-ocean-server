package com.redutec.core.repository;

import com.redutec.core.entity.CouponHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CouponHistoryRepository extends JpaRepository<CouponHistory, Integer>, JpaSpecificationExecutor<CouponHistory> {
}