package com.redutec.core.repository;

import com.redutec.core.entity.MltSubscriptionBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltSubscriptionBillRepository extends JpaRepository<MltSubscriptionBill, Integer>, JpaSpecificationExecutor<MltSubscriptionBill> {
}