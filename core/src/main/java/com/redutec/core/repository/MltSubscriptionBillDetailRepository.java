package com.redutec.core.repository;

import com.redutec.core.entity.MltSubscriptionBillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltSubscriptionBillDetailRepository extends JpaRepository<MltSubscriptionBillDetail, Integer>, JpaSpecificationExecutor<MltSubscriptionBillDetail> {
}