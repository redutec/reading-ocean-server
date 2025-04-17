package com.redutec.core.repository;

import com.redutec.core.entity.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InstituteRepository extends JpaRepository<Institute, Long>, JpaSpecificationExecutor<Institute> {
}