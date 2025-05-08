package com.redutec.core.repository;

import com.redutec.core.entity.SubscriptionInstitute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionInstituteRepository extends JpaRepository<SubscriptionInstitute, Long>, JpaSpecificationExecutor<SubscriptionInstitute> {
}
