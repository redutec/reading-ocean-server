package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.SubscriptionPass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionPassRepository extends JpaRepository<SubscriptionPass, Integer>, JpaSpecificationExecutor<SubscriptionPass> {
}