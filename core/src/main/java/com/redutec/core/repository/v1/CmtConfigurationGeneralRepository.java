package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CmtConfigurationGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CmtConfigurationGeneralRepository extends JpaRepository<CmtConfigurationGeneral, String>, JpaSpecificationExecutor<CmtConfigurationGeneral> {
}