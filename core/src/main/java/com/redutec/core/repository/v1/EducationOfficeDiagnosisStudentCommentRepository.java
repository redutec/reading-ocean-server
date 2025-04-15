package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.EducationOfficeDiagnosisStudentComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EducationOfficeDiagnosisStudentCommentRepository extends JpaRepository<EducationOfficeDiagnosisStudentComment, Integer>, JpaSpecificationExecutor<EducationOfficeDiagnosisStudentComment> {
}