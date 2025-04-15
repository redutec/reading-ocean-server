package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.ReadingLevelResultType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingLevelResultTypeRepository extends JpaRepository<ReadingLevelResultType, Integer>, JpaSpecificationExecutor<ReadingLevelResultType> {
}