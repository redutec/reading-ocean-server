package com.redutec.core.repository;

import com.redutec.core.entity.ActBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActBranchRepository extends JpaRepository<ActBranch, Long>, JpaSpecificationExecutor<ActBranch> {
}