package com.redutec.core.repository;

import com.redutec.core.entity.RatReadingMissionResult;
import com.redutec.core.entity.key.RatReadingMissionResultKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RatReadingMissionResultRepository extends JpaRepository<RatReadingMissionResult, RatReadingMissionResultKey>, JpaSpecificationExecutor<RatReadingMissionResult> {
}