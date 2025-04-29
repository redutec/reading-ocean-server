package com.redutec.core.repository;

import com.redutec.core.entity.BookGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookGroupRepository extends JpaRepository<BookGroup, Long>, JpaSpecificationExecutor<BookGroup> {}