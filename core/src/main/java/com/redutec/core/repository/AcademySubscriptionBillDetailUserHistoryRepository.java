package com.redutec.core.repository;

import com.redutec.core.entity.AcademySubscriptionBillDetailUserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AcademySubscriptionBillDetailUserHistoryRepository extends JpaRepository<AcademySubscriptionBillDetailUserHistory, Long>, JpaSpecificationExecutor<AcademySubscriptionBillDetailUserHistory> {
}