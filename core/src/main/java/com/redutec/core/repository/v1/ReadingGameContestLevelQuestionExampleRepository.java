package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.ReadingGameContestLevelQuestionExample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestLevelQuestionExampleRepository extends JpaRepository<ReadingGameContestLevelQuestionExample, Integer>, JpaSpecificationExecutor<ReadingGameContestLevelQuestionExample> {
}