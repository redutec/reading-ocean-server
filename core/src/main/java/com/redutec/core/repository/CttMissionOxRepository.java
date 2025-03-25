package com.redutec.core.repository;

import com.redutec.core.entity.CttMissionOx;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttMissionOxRepository extends JpaRepository<CttMissionOx, Integer>, JpaSpecificationExecutor<CttMissionOx> {
}