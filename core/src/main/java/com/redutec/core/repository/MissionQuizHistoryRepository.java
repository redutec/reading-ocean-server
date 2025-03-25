package com.redutec.core.repository;

import com.redutec.core.entity.MissionQuizHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissionQuizHistoryRepository extends JpaRepository<MissionQuizHistory, Integer>, JpaSpecificationExecutor<MissionQuizHistory> {
}