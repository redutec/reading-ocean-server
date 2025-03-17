package com.redutec.core.repository;

import com.redutec.core.entity.CmtProcessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CmtProcessLogRepository extends JpaRepository<CmtProcessLog, Integer>, JpaSpecificationExecutor<CmtProcessLog> {
}