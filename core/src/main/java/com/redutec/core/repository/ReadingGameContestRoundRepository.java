package com.redutec.core.repository;

import com.redutec.core.entity.ReadingGameContestRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestRoundRepository extends JpaRepository<ReadingGameContestRound, Integer>, JpaSpecificationExecutor<ReadingGameContestRound> {
}