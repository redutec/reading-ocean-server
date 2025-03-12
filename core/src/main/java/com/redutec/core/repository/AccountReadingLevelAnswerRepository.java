package com.redutec.core.repository;

import com.redutec.core.entity.AccountReadingLevelAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountReadingLevelAnswerRepository extends JpaRepository<AccountReadingLevelAnswer, Long>, JpaSpecificationExecutor<AccountReadingLevelAnswer> {
}