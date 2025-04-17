package com.redutec.core.repository;

import com.redutec.core.entity.InstituteClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface InstituteClassRepository extends JpaRepository<InstituteClass, Long>, JpaSpecificationExecutor<InstituteClass> {
}