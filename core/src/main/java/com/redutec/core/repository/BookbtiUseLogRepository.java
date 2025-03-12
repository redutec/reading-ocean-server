package com.redutec.core.repository;

import com.redutec.core.entity.BookbtiUseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookbtiUseLogRepository extends JpaRepository<BookbtiUseLog, Integer>, JpaSpecificationExecutor<BookbtiUseLog> {
}