package com.redutec.core.repository;

import com.redutec.core.entity.RatBookCart;
import com.redutec.core.entity.key.RatBookCartKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RatBookCartRepository extends JpaRepository<RatBookCart, RatBookCartKey>, JpaSpecificationExecutor<RatBookCart> {
}