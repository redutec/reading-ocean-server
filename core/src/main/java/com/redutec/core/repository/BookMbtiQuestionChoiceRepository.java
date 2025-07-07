package com.redutec.core.repository;

import com.redutec.core.entity.BookMbtiQuestionChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookMbtiQuestionChoiceRepository extends JpaRepository<BookMbtiQuestionChoice, Long>, JpaSpecificationExecutor<BookMbtiQuestionChoice> {
}