package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.RatBookCase;
import com.redutec.core.entity.v1.key.RatBookCaseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RatBookCaseRepository extends JpaRepository<RatBookCase, RatBookCaseKey>, JpaSpecificationExecutor<RatBookCase> {
}