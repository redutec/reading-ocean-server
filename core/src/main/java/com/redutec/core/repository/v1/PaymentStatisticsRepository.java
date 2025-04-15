package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.PaymentStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PaymentStatisticsRepository extends JpaRepository<PaymentStatistics, Integer>, JpaSpecificationExecutor<PaymentStatistics> {
}