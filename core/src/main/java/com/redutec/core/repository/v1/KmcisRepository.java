package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.Kmcis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface KmcisRepository extends JpaRepository<Kmcis, Integer>, JpaSpecificationExecutor<Kmcis> {
}