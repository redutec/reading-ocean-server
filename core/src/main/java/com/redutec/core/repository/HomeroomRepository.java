package com.redutec.core.repository;

import com.redutec.core.entity.Homeroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HomeroomRepository extends JpaRepository<Homeroom, Long>, JpaSpecificationExecutor<Homeroom> {
}