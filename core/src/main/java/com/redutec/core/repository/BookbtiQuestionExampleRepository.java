package com.redutec.core.repository;

import com.redutec.core.entity.BookbtiQuestionExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookbtiQuestionExampleRepository extends JpaRepository<BookbtiQuestionExample, Integer>, JpaSpecificationExecutor<BookbtiQuestionExample> {
}