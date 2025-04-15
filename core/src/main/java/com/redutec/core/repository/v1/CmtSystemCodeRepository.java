package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CmtSystemCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CmtSystemCodeRepository extends JpaRepository<CmtSystemCode, String>, JpaSpecificationExecutor<CmtSystemCode> {
}