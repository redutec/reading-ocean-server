package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.MltMallOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltMallOrderHistoryRepository extends JpaRepository<MltMallOrderHistory, Integer>, JpaSpecificationExecutor<MltMallOrderHistory> {
}