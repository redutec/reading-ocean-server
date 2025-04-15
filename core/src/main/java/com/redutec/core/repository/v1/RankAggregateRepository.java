package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.RankAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RankAggregateRepository extends JpaRepository<RankAggregate, Integer>, JpaSpecificationExecutor<RankAggregate> {
}