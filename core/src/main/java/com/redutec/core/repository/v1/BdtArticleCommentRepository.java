package com.redutec.core.repository.v1;

import com.redutec.core.entity.v1.BdtArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BdtArticleCommentRepository extends JpaRepository<BdtArticleComment, Integer>, JpaSpecificationExecutor<BdtArticleComment> {
}