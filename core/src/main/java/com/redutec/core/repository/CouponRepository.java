package com.redutec.core.repository;

import com.redutec.core.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CouponRepository extends JpaRepository<Coupon, Integer>, JpaSpecificationExecutor<Coupon> {
}