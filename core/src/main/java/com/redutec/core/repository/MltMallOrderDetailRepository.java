package com.redutec.core.repository;

import com.redutec.core.entity.MltMallOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltMallOrderDetailRepository extends JpaRepository<MltMallOrderDetail, Integer>, JpaSpecificationExecutor<MltMallOrderDetail> {
}