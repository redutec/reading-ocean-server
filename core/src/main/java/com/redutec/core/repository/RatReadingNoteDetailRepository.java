package com.redutec.core.repository;

import com.redutec.core.entity.RatReadingNoteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RatReadingNoteDetailRepository extends JpaRepository<RatReadingNoteDetail, Integer>, JpaSpecificationExecutor<RatReadingNoteDetail> {
}