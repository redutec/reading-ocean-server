package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CttMissionMatchingGameDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttMissionMatchingGameDetailRepository extends JpaRepository<CttMissionMatchingGameDetail, Integer>, JpaSpecificationExecutor<CttMissionMatchingGameDetail> {
}