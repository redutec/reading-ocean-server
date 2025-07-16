package com.redutec.core.repository;

import com.redutec.core.entity.Institute;
import com.redutec.core.entity.MeteredBilling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface MeteredBillingRepository extends JpaRepository<MeteredBilling, Long>, JpaSpecificationExecutor<MeteredBilling> {
    Optional<MeteredBilling> findByIdAndInstitute(Long meteredBillingId, Institute institute);
}