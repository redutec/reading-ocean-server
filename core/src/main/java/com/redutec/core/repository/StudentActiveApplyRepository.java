package com.redutec.core.repository;

import com.redutec.core.entity.StudentActiveApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudentActiveApplyRepository extends JpaRepository<StudentActiveApply, Long>, JpaSpecificationExecutor<StudentActiveApply> {
}