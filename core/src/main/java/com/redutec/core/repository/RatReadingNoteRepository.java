package com.redutec.core.repository;

import com.redutec.core.entity.RatReadingNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RatReadingNoteRepository extends JpaRepository<RatReadingNote, Integer>, JpaSpecificationExecutor<RatReadingNote> {
}