package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CttBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttBookRepository extends JpaRepository<CttBook, Integer>, JpaSpecificationExecutor<CttBook> {
}