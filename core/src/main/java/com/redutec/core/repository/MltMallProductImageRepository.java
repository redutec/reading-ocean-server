package com.redutec.core.repository;

import com.redutec.core.entity.MltMallProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltMallProductImageRepository extends JpaRepository<MltMallProductImage, Integer>, JpaSpecificationExecutor<MltMallProductImage> {
}