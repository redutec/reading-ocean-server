package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.ReadingLevelQuestionExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingLevelQuestionExampleRepository extends JpaRepository<ReadingLevelQuestionExample, Integer>, JpaSpecificationExecutor<ReadingLevelQuestionExample> {
}