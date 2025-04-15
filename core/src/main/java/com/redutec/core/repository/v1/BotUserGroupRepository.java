package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.BotUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BotUserGroupRepository extends JpaRepository<BotUserGroup, Long>, JpaSpecificationExecutor<BotUserGroup> {
}