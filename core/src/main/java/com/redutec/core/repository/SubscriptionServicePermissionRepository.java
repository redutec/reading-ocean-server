package com.redutec.core.repository;

import com.redutec.core.entity.SubscriptionServicePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubscriptionServicePermissionRepository extends JpaRepository<SubscriptionServicePermission, Integer>, JpaSpecificationExecutor<SubscriptionServicePermission> {
}