package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.MissionOxHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissionOxHistoryRepository extends JpaRepository<MissionOxHistory, Integer>, JpaSpecificationExecutor<MissionOxHistory> {
}