package com.redutec.core.repository;

import com.redutec.core.entity.ReadingLevelQuestionExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingLevelQuestionExampleRepository extends JpaRepository<ReadingLevelQuestionExample, Integer>, JpaSpecificationExecutor<ReadingLevelQuestionExample> {
}