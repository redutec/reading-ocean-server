package com.redutec.core.repository;

import com.redutec.core.entity.MltMallOrder;
import com.redutec.core.entity.key.MltMallOrderKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltMallOrderRepository extends JpaRepository<MltMallOrder, MltMallOrderKey>, JpaSpecificationExecutor<MltMallOrder> {
}