package com.redutec.core.repository;

import com.redutec.core.entity.CttMissionQuiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttMissionQuizRepository extends JpaRepository<CttMissionQuiz, Integer>, JpaSpecificationExecutor<CttMissionQuiz> {
}