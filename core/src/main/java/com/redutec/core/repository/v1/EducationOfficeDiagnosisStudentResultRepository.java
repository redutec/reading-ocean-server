package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.EducationOfficeDiagnosisStudentResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EducationOfficeDiagnosisStudentResultRepository
        extends JpaRepository<EducationOfficeDiagnosisStudentResult, Integer>, JpaSpecificationExecutor<EducationOfficeDiagnosisStudentResult> {
}