package com.redutec.core.repository;

import com.redutec.core.entity.ReadingGameContest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestRepository extends JpaRepository<ReadingGameContest, Integer>, JpaSpecificationExecutor<ReadingGameContest> {
}