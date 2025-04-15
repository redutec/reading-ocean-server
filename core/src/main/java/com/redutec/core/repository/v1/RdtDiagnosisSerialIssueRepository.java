package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.RdtDiagnosisSerialIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RdtDiagnosisSerialIssueRepository extends JpaRepository<RdtDiagnosisSerialIssue, Integer>, JpaSpecificationExecutor<RdtDiagnosisSerialIssue> {
}