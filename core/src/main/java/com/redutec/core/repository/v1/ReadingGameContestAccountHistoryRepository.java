package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.ReadingGameContestAccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestAccountHistoryRepository extends JpaRepository<ReadingGameContestAccountHistory, Integer>, JpaSpecificationExecutor<ReadingGameContestAccountHistory> {
}