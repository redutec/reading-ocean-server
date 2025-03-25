package com.redutec.core.repository;

import com.redutec.core.entity.ReadingLevelResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingLevelResultRepository extends JpaRepository<ReadingLevelResult, Integer>, JpaSpecificationExecutor<ReadingLevelResult> {
}