package com.redutec.core.repository;

import com.redutec.core.entity.LearningMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LearningMaterialRepository extends JpaRepository<LearningMaterial, Long>, JpaSpecificationExecutor<LearningMaterial> {
}