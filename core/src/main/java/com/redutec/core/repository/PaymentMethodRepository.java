package com.redutec.core.repository;

import com.redutec.core.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer>, JpaSpecificationExecutor<PaymentMethod> {
}