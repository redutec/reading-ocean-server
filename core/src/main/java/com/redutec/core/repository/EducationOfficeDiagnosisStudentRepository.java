package com.redutec.core.repository;

import com.redutec.core.entity.EducationOfficeDiagnosisStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EducationOfficeDiagnosisStudentRepository extends JpaRepository<EducationOfficeDiagnosisStudent, Integer>, JpaSpecificationExecutor<EducationOfficeDiagnosisStudent> {
}