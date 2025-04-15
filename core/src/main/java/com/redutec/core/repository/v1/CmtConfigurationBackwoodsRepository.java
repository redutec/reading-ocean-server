package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CmtConfigurationBackwoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CmtConfigurationBackwoodsRepository extends JpaRepository<CmtConfigurationBackwoods, Integer>, JpaSpecificationExecutor<CmtConfigurationBackwoods> {
}