package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CttReadingNoteOpinionQuest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttReadingNoteOpinionQuestRepository extends JpaRepository<CttReadingNoteOpinionQuest, Integer>, JpaSpecificationExecutor<CttReadingNoteOpinionQuest> {
}