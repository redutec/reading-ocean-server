package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.SubscriptionServicePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionServicePermissionRepository extends JpaRepository<SubscriptionServicePermission, Integer>, JpaSpecificationExecutor<SubscriptionServicePermission> {
}