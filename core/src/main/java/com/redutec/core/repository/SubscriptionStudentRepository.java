package com.redutec.core.repository;

import com.redutec.core.entity.SubscriptionStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionStudentRepository extends JpaRepository<SubscriptionStudent, Long>, JpaSpecificationExecutor<SubscriptionStudent> {
}
