package com.redutec.core.repository;

import com.redutec.core.entity.BdtArticleAttachFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BdtArticleAttachFileRepository extends JpaRepository<BdtArticleAttachFile, Integer>, JpaSpecificationExecutor<BdtArticleAttachFile> {
}