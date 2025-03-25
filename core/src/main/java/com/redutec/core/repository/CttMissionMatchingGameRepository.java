package com.redutec.core.repository;

import com.redutec.core.entity.CttMissionMatchingGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttMissionMatchingGameRepository extends JpaRepository<CttMissionMatchingGame, Integer>, JpaSpecificationExecutor<CttMissionMatchingGame> {
}