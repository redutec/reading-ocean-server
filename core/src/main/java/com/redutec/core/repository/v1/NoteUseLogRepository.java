package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.NoteUseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NoteUseLogRepository extends JpaRepository<NoteUseLog, Integer>, JpaSpecificationExecutor<NoteUseLog> {
}