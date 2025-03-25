package com.redutec.core.repository;

import com.redutec.core.entity.RdtDiagnosisSerial;
import com.redutec.core.entity.key.RdtDiagnosisSerialKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RdtDiagnosisSerialRepository extends JpaRepository<RdtDiagnosisSerial, RdtDiagnosisSerialKey>, JpaSpecificationExecutor<RdtDiagnosisSerial> {
}