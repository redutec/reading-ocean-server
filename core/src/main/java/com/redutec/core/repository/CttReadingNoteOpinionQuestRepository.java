package com.redutec.core.repository;

import com.redutec.core.entity.CttReadingNoteOpinionQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttReadingNoteOpinionQuestRepository extends JpaRepository<CttReadingNoteOpinionQuest, Integer>, JpaSpecificationExecutor<CttReadingNoteOpinionQuest> {
}