package com.redutec.core.repository;

import com.redutec.core.entity.AccountHomeStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountHomeStatisticsRepository extends JpaRepository<AccountHomeStatistics, Long>, JpaSpecificationExecutor<AccountHomeStatistics> {
}
