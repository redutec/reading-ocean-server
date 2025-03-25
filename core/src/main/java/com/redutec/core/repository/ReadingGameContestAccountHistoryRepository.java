package com.redutec.core.repository;

import com.redutec.core.entity.ReadingGameContestAccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestAccountHistoryRepository extends JpaRepository<ReadingGameContestAccountHistory, Integer>, JpaSpecificationExecutor<ReadingGameContestAccountHistory> {
}