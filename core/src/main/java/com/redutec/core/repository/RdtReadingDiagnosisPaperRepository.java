package com.redutec.core.repository;

import com.redutec.core.entity.RdtReadingDiagnosisPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RdtReadingDiagnosisPaperRepository extends JpaRepository<RdtReadingDiagnosisPaper, Integer>, JpaSpecificationExecutor<RdtReadingDiagnosisPaper> {
}