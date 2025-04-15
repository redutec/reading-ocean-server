package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.AcademySubscriptionBillDetailUserHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AcademySubscriptionBillDetailUserHistoryRepository extends JpaRepository<AcademySubscriptionBillDetailUserHistory, Long>, JpaSpecificationExecutor<AcademySubscriptionBillDetailUserHistory> {
}