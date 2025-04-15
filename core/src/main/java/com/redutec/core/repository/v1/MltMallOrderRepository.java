package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.MltMallOrder;
import com.redutec.core.entity.v1.key.MltMallOrderKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltMallOrderRepository extends JpaRepository<MltMallOrder, MltMallOrderKey>, JpaSpecificationExecutor<MltMallOrder> {
}