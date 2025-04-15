package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.RdtReadingDiagnosisCommentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RdtReadingDiagnosisCommentDetailRepository extends JpaRepository<RdtReadingDiagnosisCommentDetail, Integer>, JpaSpecificationExecutor<RdtReadingDiagnosisCommentDetail> {
}