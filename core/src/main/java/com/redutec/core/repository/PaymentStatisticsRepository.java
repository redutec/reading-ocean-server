package com.redutec.core.repository;

import com.redutec.core.entity.PaymentStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentStatisticsRepository extends JpaRepository<PaymentStatistics, Integer>, JpaSpecificationExecutor<PaymentStatistics> {
}