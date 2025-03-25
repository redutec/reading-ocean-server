package com.redutec.core.repository;

import com.redutec.core.entity.EducationOfficeDiagnosisStudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EducationOfficeDiagnosisStudentAnswerRepository extends JpaRepository<EducationOfficeDiagnosisStudentAnswer, Integer>, JpaSpecificationExecutor<EducationOfficeDiagnosisStudentAnswer> {
}