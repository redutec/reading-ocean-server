package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.EducationOfficeDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EducationOfficeDiagnosisRepository extends JpaRepository<EducationOfficeDiagnosis, Integer>, JpaSpecificationExecutor<EducationOfficeDiagnosis> {
}