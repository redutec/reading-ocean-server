package com.redutec.core.repository;

import com.redutec.core.entity.BookMbtiSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookMbtiSurveyRepository extends JpaRepository<BookMbtiSurvey, Long>, JpaSpecificationExecutor<BookMbtiSurvey> {
}