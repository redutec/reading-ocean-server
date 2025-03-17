package com.redutec.core.repository;

import com.redutec.core.entity.CouponManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CouponManageRepository extends JpaRepository<CouponManage, Integer>, JpaSpecificationExecutor<CouponManage> {
}