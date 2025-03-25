package com.redutec.core.repository;

import com.redutec.core.entity.EbookUseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EbookUseLogRepository extends JpaRepository<EbookUseLog, Integer>, JpaSpecificationExecutor<EbookUseLog> {
}