package com.redutec.core.repository;

import com.redutec.core.entity.StudentSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionStudentRepository extends JpaRepository<StudentSubscription, Long>, JpaSpecificationExecutor<StudentSubscription> {
}
