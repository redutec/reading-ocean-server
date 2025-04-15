package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.SubscriptionStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionStatisticsRepository extends JpaRepository<SubscriptionStatistics, Integer>, JpaSpecificationExecutor<SubscriptionStatistics> {
}