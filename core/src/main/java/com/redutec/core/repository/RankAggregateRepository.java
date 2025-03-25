package com.redutec.core.repository;

import com.redutec.core.entity.RankAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RankAggregateRepository extends JpaRepository<RankAggregate, Integer>, JpaSpecificationExecutor<RankAggregate> {
}