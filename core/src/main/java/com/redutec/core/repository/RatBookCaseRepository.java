package com.redutec.core.repository;

import com.redutec.core.entity.RatBookCase;
import com.redutec.core.entity.key.RatBookCaseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RatBookCaseRepository extends JpaRepository<RatBookCase, RatBookCaseKey>, JpaSpecificationExecutor<RatBookCase> {
}