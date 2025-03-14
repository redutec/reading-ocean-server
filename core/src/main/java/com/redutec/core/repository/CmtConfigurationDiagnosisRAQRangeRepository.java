package com.redutec.core.repository;

import com.redutec.core.entity.CmtConfigurationDiagnosisRAQRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CmtConfigurationDiagnosisRAQRangeRepository extends JpaRepository<CmtConfigurationDiagnosisRAQRange, String>, JpaSpecificationExecutor<CmtConfigurationDiagnosisRAQRange> {
}