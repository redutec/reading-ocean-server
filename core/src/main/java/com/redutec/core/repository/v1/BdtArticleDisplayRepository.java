package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.BdtArticle;
import com.redutec.core.entity.v1.BdtArticleDisplay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BdtArticleDisplayRepository extends JpaRepository<BdtArticleDisplay, Integer>, JpaSpecificationExecutor<BdtArticleDisplay> {
    Optional<BdtArticleDisplay> findByArticle(BdtArticle article);
}