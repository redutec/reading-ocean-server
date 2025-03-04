package com.redutec.core.repository;

import com.redutec.core.entity.ActAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActAccountRepository extends JpaRepository<ActAccount, Integer> {
}