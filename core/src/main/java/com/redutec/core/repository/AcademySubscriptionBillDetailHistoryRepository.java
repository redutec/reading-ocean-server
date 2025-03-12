package com.redutec.core.repository;

import com.redutec.core.entity.AcademySubscriptionBillDetailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AcademySubscriptionBillDetailHistoryRepository extends JpaRepository<AcademySubscriptionBillDetailHistory, Long>, JpaSpecificationExecutor<AcademySubscriptionBillDetailHistory> {
}