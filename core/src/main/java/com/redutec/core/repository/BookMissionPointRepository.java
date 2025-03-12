package com.redutec.core.repository;

import com.redutec.core.entity.BookMissionPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookMissionPointRepository extends JpaRepository<BookMissionPoint, Integer>, JpaSpecificationExecutor<BookMissionPoint> {
}