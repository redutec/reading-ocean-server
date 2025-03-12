package com.redutec.core.repository;

import com.redutec.core.entity.BotUserGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BotUserGroupRepository extends JpaRepository<BotUserGroup, Long>, JpaSpecificationExecutor<BotUserGroup> {
}