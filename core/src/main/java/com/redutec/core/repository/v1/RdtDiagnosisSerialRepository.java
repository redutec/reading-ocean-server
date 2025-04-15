package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.RdtDiagnosisSerial;
import com.redutec.core.entity.v1.key.RdtDiagnosisSerialKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RdtDiagnosisSerialRepository extends JpaRepository<RdtDiagnosisSerial, RdtDiagnosisSerialKey>, JpaSpecificationExecutor<RdtDiagnosisSerial> {
}