package com.redutec.core.repository;

import com.redutec.core.entity.RdtReadingDiagnosisReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RdtReadingDiagnosisReportRepository extends JpaRepository<RdtReadingDiagnosisReport, Integer>, JpaSpecificationExecutor<RdtReadingDiagnosisReport> {
}