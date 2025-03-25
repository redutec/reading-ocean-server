package com.redutec.core.repository;

import com.redutec.core.entity.EducationOfficeDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EducationOfficeDiagnosisRepository extends JpaRepository<EducationOfficeDiagnosis, Integer>, JpaSpecificationExecutor<EducationOfficeDiagnosis> {
}