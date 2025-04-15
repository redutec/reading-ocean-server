package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.ReadingGameContestLevelResultType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestLevelResultTypeRepository extends JpaRepository<ReadingGameContestLevelResultType, Integer>, JpaSpecificationExecutor<ReadingGameContestLevelResultType> {
}