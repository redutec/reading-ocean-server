package com.redutec.core.repository;

import com.redutec.core.entity.SubscriptionStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionStatisticsRepository extends JpaRepository<SubscriptionStatistics, Integer>, JpaSpecificationExecutor<SubscriptionStatistics> {
}