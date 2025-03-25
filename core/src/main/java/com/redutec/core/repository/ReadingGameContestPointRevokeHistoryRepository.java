package com.redutec.core.repository;

import com.redutec.core.entity.ReadingGameContestPointRevokeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestPointRevokeHistoryRepository extends JpaRepository<ReadingGameContestPointRevokeHistory, Integer>, JpaSpecificationExecutor<ReadingGameContestPointRevokeHistory> {
}