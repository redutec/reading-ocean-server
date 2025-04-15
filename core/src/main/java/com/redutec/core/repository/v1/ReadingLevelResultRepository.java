package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.ReadingLevelResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingLevelResultRepository extends JpaRepository<ReadingLevelResult, Integer>, JpaSpecificationExecutor<ReadingLevelResult> {
}