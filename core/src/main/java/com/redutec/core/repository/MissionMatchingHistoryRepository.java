package com.redutec.core.repository;

import com.redutec.core.entity.MissionMatchingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissionMatchingHistoryRepository extends JpaRepository<MissionMatchingHistory, Integer>, JpaSpecificationExecutor<MissionMatchingHistory> {
}