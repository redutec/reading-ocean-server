package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.ReadingLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingLevelRepository extends JpaRepository<ReadingLevel, Integer>, JpaSpecificationExecutor<ReadingLevel> {
}