package com.redutec.core.repository;

import com.redutec.core.entity.AccountStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountStatusHistoryRepository extends JpaRepository<AccountStatusHistory, Long>, JpaSpecificationExecutor<AccountStatusHistory> {
}