package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookFileRepository extends JpaRepository<BookFile, Integer>, JpaSpecificationExecutor<BookFile> {
}