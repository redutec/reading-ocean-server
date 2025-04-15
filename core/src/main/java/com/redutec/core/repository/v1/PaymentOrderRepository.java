package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Integer>, JpaSpecificationExecutor<PaymentOrder> {
}