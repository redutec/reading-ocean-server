package com.redutec.core.repository;

import com.redutec.core.entity.BotUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BotUserRepository extends JpaRepository<BotUser, Integer>, JpaSpecificationExecutor<BotUser> {
    @Query("SELECT bu FROM BotUser bu LEFT JOIN FETCH bu.userGroups ug LEFT JOIN FETCH ug.group WHERE bu.userNo = :userNo")
    Optional<BotUser> findByUserNoWithGroups(@Param("userNo") Integer userNo);

    Optional<BotUser> findByUserId(String userId);
}