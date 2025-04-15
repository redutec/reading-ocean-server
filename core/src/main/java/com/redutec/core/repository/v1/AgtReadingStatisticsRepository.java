package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.AgtReadingStatistics;
import com.redutec.core.entity.v1.key.AgtReadingStatisticsKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AgtReadingStatisticsRepository extends JpaRepository<AgtReadingStatistics, AgtReadingStatisticsKey>, JpaSpecificationExecutor<AgtReadingStatistics> {
}