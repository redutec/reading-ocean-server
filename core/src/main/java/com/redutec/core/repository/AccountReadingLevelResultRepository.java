package com.redutec.core.repository;

import com.redutec.core.entity.AccountReadingLevelResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountReadingLevelResultRepository extends JpaRepository<AccountReadingLevelResult, Integer>, JpaSpecificationExecutor<AccountReadingLevelResult> {
}