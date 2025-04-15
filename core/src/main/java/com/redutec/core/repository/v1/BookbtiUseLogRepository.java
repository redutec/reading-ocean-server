package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.BookbtiUseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookbtiUseLogRepository extends JpaRepository<BookbtiUseLog, Integer>, JpaSpecificationExecutor<BookbtiUseLog> {
}