package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.BookbtiResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookbtiResultRepository extends JpaRepository<BookbtiResult, Integer>, JpaSpecificationExecutor<BookbtiResult> {
}