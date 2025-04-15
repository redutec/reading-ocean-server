package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.StudentActiveApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentActiveApplyRepository extends JpaRepository<StudentActiveApply, Long>, JpaSpecificationExecutor<StudentActiveApply> {
}