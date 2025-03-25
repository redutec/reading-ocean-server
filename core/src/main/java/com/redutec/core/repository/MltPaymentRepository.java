package com.redutec.core.repository;

import com.redutec.core.entity.MltPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltPaymentRepository extends JpaRepository<MltPayment, Integer>, JpaSpecificationExecutor<MltPayment> {
}