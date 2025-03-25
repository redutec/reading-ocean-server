package com.redutec.core.repository;

import com.redutec.core.entity.ReadingLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingLevelRepository extends JpaRepository<ReadingLevel, Integer>, JpaSpecificationExecutor<ReadingLevel> {
}