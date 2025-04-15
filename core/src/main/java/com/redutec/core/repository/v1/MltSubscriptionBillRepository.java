package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.MltSubscriptionBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltSubscriptionBillRepository extends JpaRepository<MltSubscriptionBill, Integer>, JpaSpecificationExecutor<MltSubscriptionBill> {
}