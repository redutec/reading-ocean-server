package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.AccountBookbtiResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountBookbtiResultRepository extends JpaRepository<AccountBookbtiResult, Long>, JpaSpecificationExecutor<AccountBookbtiResult> {
}
