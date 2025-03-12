package com.redutec.core.repository;

import com.redutec.core.entity.BotGroupPermission;
import com.redutec.core.entity.key.BotGroupPermissionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BotGroupPermissionRepository extends JpaRepository<BotGroupPermission, BotGroupPermissionKey>, JpaSpecificationExecutor<BotGroupPermission> {
}