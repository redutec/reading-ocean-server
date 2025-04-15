package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.RdtReadingDiagnosisReportComment;
import com.redutec.core.entity.v1.key.RdtReadingDiagnosisReportCommentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RdtReadingDiagnosisReportCommentRepository extends JpaRepository<RdtReadingDiagnosisReportComment, RdtReadingDiagnosisReportCommentKey>, JpaSpecificationExecutor<RdtReadingDiagnosisReportComment> {
}