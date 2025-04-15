package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CttBookGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttBookGroupRepository extends JpaRepository<CttBookGroup, Integer>, JpaSpecificationExecutor<CttBookGroup> {
}