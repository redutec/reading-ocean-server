package com.redutec.core.repository;

import com.redutec.core.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderInstituteRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
}