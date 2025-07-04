package com.redutec.core.repository;

import com.redutec.core.entity.BookMbtiQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookMbtiQuestionRepository extends JpaRepository<BookMbtiQuestion, Long>, JpaSpecificationExecutor<BookMbtiQuestion> {
}