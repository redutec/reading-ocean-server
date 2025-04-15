package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CmtConfigurationKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CmtConfigurationKeywordRepository extends JpaRepository<CmtConfigurationKeyword, String>, JpaSpecificationExecutor<CmtConfigurationKeyword> {
}