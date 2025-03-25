package com.redutec.core.repository;

import com.redutec.core.entity.MltPaymentReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltPaymentReceiptRepository extends JpaRepository<MltPaymentReceipt, Integer>, JpaSpecificationExecutor<MltPaymentReceipt> {
}