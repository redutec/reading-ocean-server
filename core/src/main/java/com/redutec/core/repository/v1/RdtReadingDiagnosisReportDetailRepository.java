package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.RdtReadingDiagnosisReportDetail;
import com.redutec.core.entity.v1.key.RdtReadingDiagnosisReportDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RdtReadingDiagnosisReportDetailRepository extends JpaRepository<RdtReadingDiagnosisReportDetail, RdtReadingDiagnosisReportDetailKey>, JpaSpecificationExecutor<RdtReadingDiagnosisReportDetail> {
}