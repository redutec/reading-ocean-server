package com.redutec.core.repository;

import com.redutec.core.entity.BdtArticle;
import com.redutec.core.entity.BdtArticleAttachFile;
import com.redutec.core.meta.AttachFileValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BdtArticleAttachFileRepository extends JpaRepository<BdtArticleAttachFile, Integer>, JpaSpecificationExecutor<BdtArticleAttachFile> {
    Optional<BdtArticleAttachFile> findByArticleAndAttachFileValue(BdtArticle bdtArticle, AttachFileValue attachFileValue);

    void deleteByArticle(BdtArticle article);
}