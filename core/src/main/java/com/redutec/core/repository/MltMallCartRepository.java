package com.redutec.core.repository;

import com.redutec.core.entity.MltMallCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltMallCartRepository extends JpaRepository<MltMallCart, Integer>, JpaSpecificationExecutor<MltMallCart> {
}