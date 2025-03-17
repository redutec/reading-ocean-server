package com.redutec.core.repository;

import com.redutec.core.entity.CttBookGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttBookGroupRepository extends JpaRepository<CttBookGroup, Integer>, JpaSpecificationExecutor<CttBookGroup> {
}