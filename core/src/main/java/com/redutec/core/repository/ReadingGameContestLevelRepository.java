package com.redutec.core.repository;

import com.redutec.core.entity.ReadingGameContestLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestLevelRepository extends JpaRepository<ReadingGameContestLevel, Integer>, JpaSpecificationExecutor<ReadingGameContestLevel> {
}