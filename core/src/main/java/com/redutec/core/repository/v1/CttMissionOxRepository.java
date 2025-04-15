package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CttMissionOx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttMissionOxRepository extends JpaRepository<CttMissionOx, Integer>, JpaSpecificationExecutor<CttMissionOx> {
}