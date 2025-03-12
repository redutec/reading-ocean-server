package com.redutec.core.repository;

import com.redutec.core.entity.ActAcademy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActAcademyRepository extends JpaRepository<ActAcademy, Integer>, JpaSpecificationExecutor<ActAcademy> {
}