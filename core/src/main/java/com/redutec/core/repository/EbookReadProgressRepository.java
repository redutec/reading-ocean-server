package com.redutec.core.repository;

import com.redutec.core.entity.EbookReadProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EbookReadProgressRepository extends JpaRepository<EbookReadProgress, Integer>, JpaSpecificationExecutor<EbookReadProgress> {
}