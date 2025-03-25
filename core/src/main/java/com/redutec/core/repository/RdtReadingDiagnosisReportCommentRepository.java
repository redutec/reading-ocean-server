package com.redutec.core.repository;

import com.redutec.core.entity.RdtReadingDiagnosisReportComment;
import com.redutec.core.entity.key.RdtReadingDiagnosisReportCommentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RdtReadingDiagnosisReportCommentRepository extends JpaRepository<RdtReadingDiagnosisReportComment, RdtReadingDiagnosisReportCommentKey>, JpaSpecificationExecutor<RdtReadingDiagnosisReportComment> {
}