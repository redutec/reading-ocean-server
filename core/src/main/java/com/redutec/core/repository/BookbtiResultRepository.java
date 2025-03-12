package com.redutec.core.repository;

import com.redutec.core.entity.BookbtiResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookbtiResultRepository extends JpaRepository<BookbtiResult, Integer>, JpaSpecificationExecutor<BookbtiResult> {
}