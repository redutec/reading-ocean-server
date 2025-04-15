package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CouponManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CouponManageRepository extends JpaRepository<CouponManage, Integer>, JpaSpecificationExecutor<CouponManage> {
}