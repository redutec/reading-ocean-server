package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.AccountStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountStatusHistoryRepository extends JpaRepository<AccountStatusHistory, Long>, JpaSpecificationExecutor<AccountStatusHistory> {
}