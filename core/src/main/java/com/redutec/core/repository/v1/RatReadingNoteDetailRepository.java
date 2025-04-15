package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.RatReadingNoteDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RatReadingNoteDetailRepository extends JpaRepository<RatReadingNoteDetail, Integer>, JpaSpecificationExecutor<RatReadingNoteDetail> {
}