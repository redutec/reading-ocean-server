package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.ReadingGameContestAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestAccountRepository extends JpaRepository<ReadingGameContestAccount, Integer>, JpaSpecificationExecutor<ReadingGameContestAccount> {
}