package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.MltPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltPaymentRepository extends JpaRepository<MltPayment, Integer>, JpaSpecificationExecutor<MltPayment> {
}