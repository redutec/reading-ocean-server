package com.redutec.core.repository;

import com.redutec.core.entity.ReadingLevelQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingLevelQuestionRepository extends JpaRepository<ReadingLevelQuestion, Integer>, JpaSpecificationExecutor<ReadingLevelQuestion> {
}