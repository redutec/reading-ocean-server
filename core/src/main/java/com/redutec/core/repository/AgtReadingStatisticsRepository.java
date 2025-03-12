package com.redutec.core.repository;

import com.redutec.core.entity.AgtReadingStatistics;
import com.redutec.core.entity.key.AgtReadingStatisticsKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AgtReadingStatisticsRepository extends JpaRepository<AgtReadingStatistics, AgtReadingStatisticsKey>, JpaSpecificationExecutor<AgtReadingStatistics> {
}