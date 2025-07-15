package com.redutec.core.repository;

import com.redutec.core.entity.MeteredBilling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MeteredBillingRepository extends JpaRepository<MeteredBilling, Long>, JpaSpecificationExecutor<MeteredBilling> {
}