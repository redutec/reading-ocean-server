package com.redutec.core.repository;

import com.redutec.core.entity.BdtArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BdtArticleRepository extends JpaRepository<BdtArticle, Integer>, JpaSpecificationExecutor<BdtArticle> {
}