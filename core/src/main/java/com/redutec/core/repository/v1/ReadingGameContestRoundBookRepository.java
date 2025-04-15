package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.ReadingGameContestRoundBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ReadingGameContestRoundBookRepository extends JpaRepository<ReadingGameContestRoundBook, Integer>, JpaSpecificationExecutor<ReadingGameContestRoundBook> {
}