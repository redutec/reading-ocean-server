package com.redutec.core.repository;

import com.redutec.core.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Integer>, JpaSpecificationExecutor<LoginHistory> {
}