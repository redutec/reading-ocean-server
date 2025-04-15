package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.ReadingGameContest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestRepository extends JpaRepository<ReadingGameContest, Integer>, JpaSpecificationExecutor<ReadingGameContest> {
}