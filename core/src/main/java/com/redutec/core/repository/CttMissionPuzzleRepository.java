package com.redutec.core.repository;

import com.redutec.core.entity.CttMissionPuzzle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttMissionPuzzleRepository extends JpaRepository<CttMissionPuzzle, Integer>, JpaSpecificationExecutor<CttMissionPuzzle> {
}