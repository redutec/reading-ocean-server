package com.redutec.core.repository;

import com.redutec.core.entity.Institute;
import com.redutec.core.entity.InstituteOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstituteOrderRepository extends JpaRepository<InstituteOrder, Long>, JpaSpecificationExecutor<InstituteOrder> {
    Optional<InstituteOrder> findByInstitute(Institute institute);
}