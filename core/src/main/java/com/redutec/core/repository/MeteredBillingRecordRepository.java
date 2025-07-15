package com.redutec.core.repository;

import com.redutec.core.entity.MeteredBillingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MeteredBillingRecordRepository extends JpaRepository<MeteredBillingRecord, Long>, JpaSpecificationExecutor<MeteredBillingRecord> {
}