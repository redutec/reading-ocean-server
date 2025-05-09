package com.redutec.core.repository;

import com.redutec.core.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BannerRepository extends JpaRepository<Banner, Long>, JpaSpecificationExecutor<Banner> {
}