package com.redutec.core.repository;

import com.redutec.core.entity.BotMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BotMenuRepository extends JpaRepository<BotMenu, Integer>, JpaSpecificationExecutor<BotMenu> {
}