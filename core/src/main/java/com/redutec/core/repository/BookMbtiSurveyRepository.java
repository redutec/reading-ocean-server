package com.redutec.core.repository;

import com.redutec.core.entity.BookMbtiSurvey;
import com.redutec.core.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BookMbtiSurveyRepository extends JpaRepository<BookMbtiSurvey, Long>, JpaSpecificationExecutor<BookMbtiSurvey> {
    Optional<BookMbtiSurvey> findFirstByStudentOrderByCreatedAtDesc(Student student);
}