package com.redutec.core.repository;

import com.redutec.core.entity.MissionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissionHistoryRepository extends JpaRepository<MissionHistory, Integer>, JpaSpecificationExecutor<MissionHistory> {
}