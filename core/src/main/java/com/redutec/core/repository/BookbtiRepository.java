package com.redutec.core.repository;

import com.redutec.core.entity.Bookbti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookbtiRepository extends JpaRepository<Bookbti, Integer>, JpaSpecificationExecutor<Bookbti> {
}