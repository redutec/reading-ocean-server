package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.BotGroupPermission;
import com.redutec.core.entity.v1.key.BotGroupPermissionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BotGroupPermissionRepository extends JpaRepository<BotGroupPermission, BotGroupPermissionKey>, JpaSpecificationExecutor<BotGroupPermission> {
}