package com.redutec.core.repository;

import com.redutec.core.entity.CmtConfigurationKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CmtConfigurationKeywordRepository extends JpaRepository<CmtConfigurationKeyword, String>, JpaSpecificationExecutor<CmtConfigurationKeyword> {
}