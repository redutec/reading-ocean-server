package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.BotGroup;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BotGroupRepository extends JpaRepository<BotGroup, Long>, JpaSpecificationExecutor<BotGroup> {
    @EntityGraph(attributePaths = {"userGroups", "groupPermissions"})
    Optional<BotGroup> findByGroupNo(Integer groupNo);
}