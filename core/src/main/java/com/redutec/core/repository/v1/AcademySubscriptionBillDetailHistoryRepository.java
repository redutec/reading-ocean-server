package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.AcademySubscriptionBillDetailHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AcademySubscriptionBillDetailHistoryRepository extends JpaRepository<AcademySubscriptionBillDetailHistory, Long>, JpaSpecificationExecutor<AcademySubscriptionBillDetailHistory> {
}