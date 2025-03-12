package com.redutec.core.repository;

import com.redutec.core.entity.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BotUserRepository extends JpaRepository<BotUser, Integer>, JpaSpecificationExecutor<BotUser> {
    Optional<BotUser> findByUserId(String userId);
}