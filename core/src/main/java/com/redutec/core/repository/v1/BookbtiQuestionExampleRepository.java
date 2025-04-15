package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.BookbtiQuestionExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookbtiQuestionExampleRepository extends JpaRepository<BookbtiQuestionExample, Integer>, JpaSpecificationExecutor<BookbtiQuestionExample> {
}