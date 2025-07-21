package com.redutec.core.repository;

import com.redutec.core.entity.CurriculumBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CurriculumBookRepository extends JpaRepository<CurriculumBook, Long>, JpaSpecificationExecutor<CurriculumBook> {
}