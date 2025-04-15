package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.EbookUseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EbookUseLogRepository extends JpaRepository<EbookUseLog, Integer>, JpaSpecificationExecutor<EbookUseLog> {
}