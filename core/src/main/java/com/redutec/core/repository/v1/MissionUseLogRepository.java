package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.MissionUseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MissionUseLogRepository extends JpaRepository<MissionUseLog, Integer>, JpaSpecificationExecutor<MissionUseLog> {
}