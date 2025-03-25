package com.redutec.core.repository;

import com.redutec.core.entity.ReadingGameContestRoundBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestRoundBookRepository extends JpaRepository<ReadingGameContestRoundBook, Integer>, JpaSpecificationExecutor<ReadingGameContestRoundBook> {
}