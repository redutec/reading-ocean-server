package com.redutec.core.repository;

import com.redutec.core.entity.BdtArticleBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BdtArticleBoardRepository extends JpaRepository<BdtArticleBoard, Integer>, JpaSpecificationExecutor<BdtArticleBoard> {
}