package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.MltMallProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MltMallProductImageRepository extends JpaRepository<MltMallProductImage, Integer>, JpaSpecificationExecutor<MltMallProductImage> {
}