package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CttMissionMatchingGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttMissionMatchingGameRepository extends JpaRepository<CttMissionMatchingGame, Integer>, JpaSpecificationExecutor<CttMissionMatchingGame> {
}