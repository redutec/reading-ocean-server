package com.redutec.core.repository;

import com.redutec.core.entity.RdtReadingDiagnosisCommentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RdtReadingDiagnosisCommentDetailRepository extends JpaRepository<RdtReadingDiagnosisCommentDetail, Integer>, JpaSpecificationExecutor<RdtReadingDiagnosisCommentDetail> {
}