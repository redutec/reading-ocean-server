package com.redutec.core.repository;

import com.redutec.core.entity.BdtArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BdtArticleCommentRepository extends JpaRepository<BdtArticleComment, Integer>, JpaSpecificationExecutor<BdtArticleComment> {
}