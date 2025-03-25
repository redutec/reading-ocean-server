package com.redutec.core.repository;

import com.redutec.core.entity.ReadingGameContestLevelQuestionExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestLevelQuestionExampleRepository extends JpaRepository<ReadingGameContestLevelQuestionExample, Integer>, JpaSpecificationExecutor<ReadingGameContestLevelQuestionExample> {
}