package com.redutec.core.repository;

import com.redutec.core.entity.ActAcademyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActAcademyClassRepository extends JpaRepository<ActAcademyClass, Integer>, JpaSpecificationExecutor<ActAcademyClass> {
}