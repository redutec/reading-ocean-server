package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CttUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttUnitRepository extends JpaRepository<CttUnit, Integer>, JpaSpecificationExecutor<CttUnit> {
}