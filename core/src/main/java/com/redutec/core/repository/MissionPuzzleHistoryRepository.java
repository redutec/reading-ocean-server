package com.redutec.core.repository;

import com.redutec.core.entity.MissionPuzzleHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissionPuzzleHistoryRepository extends JpaRepository<MissionPuzzleHistory, Integer>, JpaSpecificationExecutor<MissionPuzzleHistory> {
}