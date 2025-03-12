package com.redutec.core.repository;

import com.redutec.core.entity.ActAcademyClassAccount;
import com.redutec.core.entity.key.ActAcademyClassAccountKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActAcademyClassAccountRepository extends JpaRepository<ActAcademyClassAccount, ActAcademyClassAccountKey>, JpaSpecificationExecutor<ActAcademyClassAccount> {
}