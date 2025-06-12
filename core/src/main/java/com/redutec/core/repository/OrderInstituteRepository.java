package com.redutec.core.repository;

import com.redutec.core.entity.InstituteOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderInstituteRepository extends JpaRepository<InstituteOrder, Long>, JpaSpecificationExecutor<InstituteOrder> {
}