package com.redutec.core.repository;

import com.redutec.core.entity.RatReadingMissionQuizResult;
import com.redutec.core.entity.key.RatReadingMissionQuizResultKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RatReadingMissionQuizResultRepository extends JpaRepository<RatReadingMissionQuizResult, RatReadingMissionQuizResultKey>, JpaSpecificationExecutor<RatReadingMissionQuizResult> {
}