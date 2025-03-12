package com.redutec.core.repository;

import com.redutec.core.entity.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookFileRepository extends JpaRepository<BookFile, Integer>, JpaSpecificationExecutor<BookFile> {
}