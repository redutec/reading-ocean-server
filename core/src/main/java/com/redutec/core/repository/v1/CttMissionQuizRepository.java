package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CttMissionQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttMissionQuizRepository extends JpaRepository<CttMissionQuiz, Integer>, JpaSpecificationExecutor<CttMissionQuiz> {
}