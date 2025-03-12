package com.redutec.core.repository;

import com.redutec.core.entity.BdtArticleDisplay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BdtArticleDisplayRepository extends JpaRepository<BdtArticleDisplay, Integer>, JpaSpecificationExecutor<BdtArticleDisplay> {
}