package com.redutec.core.repository;

import com.redutec.core.entity.NoteUseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NoteUseLogRepository extends JpaRepository<NoteUseLog, Integer>, JpaSpecificationExecutor<NoteUseLog> {
}