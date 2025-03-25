package com.redutec.core.repository;

import com.redutec.core.entity.ReadingGameContestLevelResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestLevelResultRepository extends JpaRepository<ReadingGameContestLevelResult, Integer>, JpaSpecificationExecutor<ReadingGameContestLevelResult> {
}