package com.redutec.core.repository;

import com.redutec.core.entity.BotGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BotGroupRepository extends JpaRepository<BotGroup, Long>, JpaSpecificationExecutor<BotGroup> {
}