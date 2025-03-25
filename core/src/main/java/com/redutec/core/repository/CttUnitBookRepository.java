package com.redutec.core.repository;

import com.redutec.core.entity.CttUnitBook;
import com.redutec.core.entity.key.CttUnitBookKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttUnitBookRepository extends JpaRepository<CttUnitBook, CttUnitBookKey>, JpaSpecificationExecutor<CttUnitBook> {
}