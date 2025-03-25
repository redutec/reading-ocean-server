package com.redutec.core.repository;

import com.redutec.core.entity.MissionOxHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissionOxHistoryRepository extends JpaRepository<MissionOxHistory, Integer>, JpaSpecificationExecutor<MissionOxHistory> {
}