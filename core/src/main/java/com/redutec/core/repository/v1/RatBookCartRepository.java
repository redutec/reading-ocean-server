package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.RatBookCart;
import com.redutec.core.entity.v1.key.RatBookCartKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RatBookCartRepository extends JpaRepository<RatBookCart, RatBookCartKey>, JpaSpecificationExecutor<RatBookCart> {
}