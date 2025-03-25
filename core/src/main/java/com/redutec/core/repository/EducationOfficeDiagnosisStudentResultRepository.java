package com.redutec.core.repository;

import com.redutec.core.entity.EducationOfficeDiagnosisStudentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EducationOfficeDiagnosisStudentResultRepository
        extends JpaRepository<EducationOfficeDiagnosisStudentResult, Integer>, JpaSpecificationExecutor<EducationOfficeDiagnosisStudentResult> {
}