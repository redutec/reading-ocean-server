package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.BdtArticleBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BdtArticleBoardRepository extends JpaRepository<BdtArticleBoard, Integer>, JpaSpecificationExecutor<BdtArticleBoard> {
}