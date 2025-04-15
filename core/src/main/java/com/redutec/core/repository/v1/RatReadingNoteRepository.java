package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.RatReadingNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RatReadingNoteRepository extends JpaRepository<RatReadingNote, Integer>, JpaSpecificationExecutor<RatReadingNote> {
}