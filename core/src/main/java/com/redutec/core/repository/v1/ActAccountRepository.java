package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.ActAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActAccountRepository extends JpaRepository<ActAccount, Integer>, JpaSpecificationExecutor<ActAccount> {
}