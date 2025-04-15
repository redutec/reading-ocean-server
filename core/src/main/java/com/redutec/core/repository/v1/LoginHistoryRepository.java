package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Integer>, JpaSpecificationExecutor<LoginHistory> {
}