package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.MltMallOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltMallOrderDetailRepository extends JpaRepository<MltMallOrderDetail, Integer>, JpaSpecificationExecutor<MltMallOrderDetail> {
}