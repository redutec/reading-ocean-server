package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CmtConfigurationDiagnosisReadingLevel;
import com.redutec.core.entity.v1.key.CmtConfigurationDiagnosisReadingLevelKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CmtConfigurationDiagnosisReadingLevelRepository
        extends JpaRepository<CmtConfigurationDiagnosisReadingLevel, CmtConfigurationDiagnosisReadingLevelKey>,
        JpaSpecificationExecutor<CmtConfigurationDiagnosisReadingLevel> {
}