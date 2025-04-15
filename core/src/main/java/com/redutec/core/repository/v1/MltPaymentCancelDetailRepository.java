package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.MltPaymentCancelDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltPaymentCancelDetailRepository extends JpaRepository<MltPaymentCancelDetail, Integer>, JpaSpecificationExecutor<MltPaymentCancelDetail> {
}