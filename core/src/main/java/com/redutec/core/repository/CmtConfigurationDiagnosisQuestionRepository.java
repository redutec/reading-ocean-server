package com.redutec.core.repository;

import com.redutec.core.entity.CmtConfigurationDiagnosisQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CmtConfigurationDiagnosisQuestionRepository extends JpaRepository<CmtConfigurationDiagnosisQuestion, Integer>, JpaSpecificationExecutor<CmtConfigurationDiagnosisQuestion> {
}