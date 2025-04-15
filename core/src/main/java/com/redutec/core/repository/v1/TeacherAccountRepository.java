package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.TeacherAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeacherAccountRepository extends JpaRepository<TeacherAccount, Integer>, JpaSpecificationExecutor<TeacherAccount> {
}