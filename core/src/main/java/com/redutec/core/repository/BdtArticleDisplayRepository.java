package com.redutec.core.repository;

import com.redutec.core.entity.BdtArticle;
import com.redutec.core.entity.BdtArticleDisplay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BdtArticleDisplayRepository extends JpaRepository<BdtArticleDisplay, Integer>, JpaSpecificationExecutor<BdtArticleDisplay> {
    Optional<BdtArticleDisplay> findByArticle(BdtArticle article);
}