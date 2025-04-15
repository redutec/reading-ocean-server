package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.ReadingGameContestLevelQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestLevelQuestionRepository extends JpaRepository<ReadingGameContestLevelQuestion, Integer>, JpaSpecificationExecutor<ReadingGameContestLevelQuestion> {
}