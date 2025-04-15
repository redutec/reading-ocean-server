package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.RdtReadingDiagnosisApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RdtReadingDiagnosisApplyRepository extends JpaRepository<RdtReadingDiagnosisApply, Integer>, JpaSpecificationExecutor<RdtReadingDiagnosisApply> {
}