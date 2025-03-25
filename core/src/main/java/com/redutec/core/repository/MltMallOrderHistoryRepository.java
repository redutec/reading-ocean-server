package com.redutec.core.repository;

import com.redutec.core.entity.MltMallOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltMallOrderHistoryRepository extends JpaRepository<MltMallOrderHistory, Integer>, JpaSpecificationExecutor<MltMallOrderHistory> {
}