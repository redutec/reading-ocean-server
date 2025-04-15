package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CttMissionPuzzle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttMissionPuzzleRepository extends JpaRepository<CttMissionPuzzle, Integer>, JpaSpecificationExecutor<CttMissionPuzzle> {
}