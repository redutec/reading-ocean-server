package com.redutec.core.repository;

import com.redutec.core.entity.RdtReadingDiagnosisReportDetail;
import com.redutec.core.entity.key.RdtReadingDiagnosisReportDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RdtReadingDiagnosisReportDetailRepository extends JpaRepository<RdtReadingDiagnosisReportDetail, RdtReadingDiagnosisReportDetailKey>, JpaSpecificationExecutor<RdtReadingDiagnosisReportDetail> {
}