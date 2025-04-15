package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.MissionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissionHistoryRepository extends JpaRepository<MissionHistory, Integer>, JpaSpecificationExecutor<MissionHistory> {
}