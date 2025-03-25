package com.redutec.core.repository;

import com.redutec.core.entity.RdtReadingDiagnosisResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RdtReadingDiagnosisResultRepository extends JpaRepository<RdtReadingDiagnosisResult, Integer>, JpaSpecificationExecutor<RdtReadingDiagnosisResult> {
}