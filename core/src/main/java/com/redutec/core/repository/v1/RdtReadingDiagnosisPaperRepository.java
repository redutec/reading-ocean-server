package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.RdtReadingDiagnosisPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RdtReadingDiagnosisPaperRepository extends JpaRepository<RdtReadingDiagnosisPaper, Integer>, JpaSpecificationExecutor<RdtReadingDiagnosisPaper> {
}