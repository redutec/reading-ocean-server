package com.redutec.core.repository;

import com.redutec.core.entity.EducationOfficeDiagnosisStudentComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EducationOfficeDiagnosisStudentCommentRepository extends JpaRepository<EducationOfficeDiagnosisStudentComment, Integer>, JpaSpecificationExecutor<EducationOfficeDiagnosisStudentComment> {
}