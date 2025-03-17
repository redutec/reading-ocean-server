package com.redutec.core.repository;

import com.redutec.core.entity.CmtSystemCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CmtSystemCodeRepository extends JpaRepository<CmtSystemCode, String>, JpaSpecificationExecutor<CmtSystemCode> {
}