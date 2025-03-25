package com.redutec.core.repository;

import com.redutec.core.entity.ReadingLevelResultType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingLevelResultTypeRepository extends JpaRepository<ReadingLevelResultType, Integer>, JpaSpecificationExecutor<ReadingLevelResultType> {
}