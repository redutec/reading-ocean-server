package com.redutec.core.repository;

import com.redutec.core.entity.TeacherAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeacherAccountRepository extends JpaRepository<TeacherAccount, Integer>, JpaSpecificationExecutor<TeacherAccount> {
}