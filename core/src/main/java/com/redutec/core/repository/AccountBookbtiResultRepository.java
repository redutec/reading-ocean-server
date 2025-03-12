package com.redutec.core.repository;

import com.redutec.core.entity.AccountBookbtiResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountBookbtiResultRepository extends JpaRepository<AccountBookbtiResult, Long>, JpaSpecificationExecutor<AccountBookbtiResult> {
}
