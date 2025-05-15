package com.redutec.core.repository;

import com.redutec.core.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long>, JpaSpecificationExecutor<Branch> {
    Optional<Branch> findByName(String branchName);
    Optional<Branch> findByManagerTeacherId(Long managerTeacherId);
}