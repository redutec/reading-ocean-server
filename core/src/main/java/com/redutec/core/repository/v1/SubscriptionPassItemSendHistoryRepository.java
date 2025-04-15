package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.SubscriptionPassItemSendHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionPassItemSendHistoryRepository extends JpaRepository<SubscriptionPassItemSendHistory, Integer>, JpaSpecificationExecutor<SubscriptionPassItemSendHistory> {
}