package com.redutec.core.repository;

import com.redutec.core.entity.ReadingLevelUseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingLevelUseLogRepository extends JpaRepository<ReadingLevelUseLog, Integer>, JpaSpecificationExecutor<ReadingLevelUseLog> {
}