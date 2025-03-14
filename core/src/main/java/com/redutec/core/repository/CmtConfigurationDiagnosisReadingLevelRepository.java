package com.redutec.core.repository;

import com.redutec.core.entity.CmtConfigurationDiagnosisReadingLevel;
import com.redutec.core.entity.key.CmtConfigurationDiagnosisReadingLevelKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CmtConfigurationDiagnosisReadingLevelRepository
        extends JpaRepository<CmtConfigurationDiagnosisReadingLevel, CmtConfigurationDiagnosisReadingLevelKey>,
        JpaSpecificationExecutor<CmtConfigurationDiagnosisReadingLevel> {
}