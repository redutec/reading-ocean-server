package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.Bookbti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookbtiRepository extends JpaRepository<Bookbti, Integer>, JpaSpecificationExecutor<Bookbti> {
}