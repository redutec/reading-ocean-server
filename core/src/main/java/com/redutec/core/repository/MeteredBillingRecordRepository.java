package com.redutec.core.repository;

import com.redutec.core.entity.Institute;
import com.redutec.core.entity.MeteredBillingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface MeteredBillingRecordRepository extends JpaRepository<MeteredBillingRecord, Long>, JpaSpecificationExecutor<MeteredBillingRecord> {
    List<MeteredBillingRecord> findByUsageDateBetween(LocalDate startDate, LocalDate endDate);
    List<MeteredBillingRecord> findByInstituteAndUsageDateBetween(Institute institute, LocalDate billingPeriodStart, LocalDate billingPeriodEnd);
}