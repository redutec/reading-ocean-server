package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.ActAcademyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActAcademyClassRepository extends JpaRepository<ActAcademyClass, Integer>, JpaSpecificationExecutor<ActAcademyClass> {
}