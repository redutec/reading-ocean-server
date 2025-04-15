package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.CttUnitBook;
import com.redutec.core.entity.v1.key.CttUnitBookKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CttUnitBookRepository extends JpaRepository<CttUnitBook, CttUnitBookKey>, JpaSpecificationExecutor<CttUnitBook> {
}