package com.redutec.core.repository;

import com.redutec.core.entity.RdtDiagnosisSerialIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RdtDiagnosisSerialIssueRepository extends JpaRepository<RdtDiagnosisSerialIssue, Integer>, JpaSpecificationExecutor<RdtDiagnosisSerialIssue> {
}