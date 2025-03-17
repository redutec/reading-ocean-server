package com.redutec.core.repository;

import com.redutec.core.entity.CttBookGroupMember;
import com.redutec.core.entity.key.CttBookGroupMemberKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttBookGroupMemberRepository extends JpaRepository<CttBookGroupMember, CttBookGroupMemberKey>, JpaSpecificationExecutor<CttBookGroupMember> {
}