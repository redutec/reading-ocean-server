package com.redutec.core.repository;

import com.redutec.core.entity.InstituteSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteSubscriptionRepository extends JpaRepository<InstituteSubscription, Long>, JpaSpecificationExecutor<InstituteSubscription> {
}
