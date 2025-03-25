package com.redutec.core.repository;

import com.redutec.core.entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Integer>, JpaSpecificationExecutor<PaymentOrder> {
}