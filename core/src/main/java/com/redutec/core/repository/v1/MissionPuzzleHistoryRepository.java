package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.MissionPuzzleHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissionPuzzleHistoryRepository extends JpaRepository<MissionPuzzleHistory, Integer>, JpaSpecificationExecutor<MissionPuzzleHistory> {
}