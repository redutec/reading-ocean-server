package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.AccountHomeStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountHomeStatisticsRepository extends JpaRepository<AccountHomeStatistics, Long>, JpaSpecificationExecutor<AccountHomeStatistics> {
}
