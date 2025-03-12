package com.redutec.core.repository;

import com.redutec.core.entity.AccountBookbtiAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountBookbtiAnswerRepository extends JpaRepository<AccountBookbtiAnswer, Long>, JpaSpecificationExecutor<AccountBookbtiAnswer> {
}