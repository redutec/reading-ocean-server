package com.redutec.core.repository;

import com.redutec.core.entity.CttBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttBookRepository extends JpaRepository<CttBook, Integer>, JpaSpecificationExecutor<CttBook> {
}