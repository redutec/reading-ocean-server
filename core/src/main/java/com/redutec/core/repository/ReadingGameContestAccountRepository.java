package com.redutec.core.repository;

import com.redutec.core.entity.ReadingGameContestAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestAccountRepository extends JpaRepository<ReadingGameContestAccount, Integer>, JpaSpecificationExecutor<ReadingGameContestAccount> {
}