package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.ReadingGameContestRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestRoundRepository extends JpaRepository<ReadingGameContestRound, Integer>, JpaSpecificationExecutor<ReadingGameContestRound> {
}