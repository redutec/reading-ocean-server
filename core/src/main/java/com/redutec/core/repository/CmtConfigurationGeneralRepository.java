package com.redutec.core.repository;

import com.redutec.core.entity.CmtConfigurationGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CmtConfigurationGeneralRepository extends JpaRepository<CmtConfigurationGeneral, String>, JpaSpecificationExecutor<CmtConfigurationGeneral> {
}