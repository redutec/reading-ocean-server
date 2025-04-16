package com.redutec.admin.v1.article.service;

import com.redutec.core.entity.v1.BdtArticle;
import com.redutec.core.entity.v1.BdtArticleAttachFile;
import com.redutec.core.entity.v1.BdtArticleDisplay;
import com.redutec.core.meta.AttachFileValue;

public interface ArticleService {
    /**
     * 특정 게시물 엔티티 조회
     * @param articleNo 게시물 고유번호
     * @return 특정 게시물 엔티티
     */
    BdtArticle getBdtArticleByArticleNo(Integer articleNo);

    /**
     * 특정 게시물 노출정보 엔티티 조회
     * @param article 게시물 엔티티
     * @return 특정 게시물 노출정보 엔티티(Nullable)
     */
    BdtArticleDisplay findBdtArticleDisplayByArticle(BdtArticle article);

    /**
     * 특정 게시물 첨부파일 엔티티 조회
     * @param article 게시물 엔티티
     * @param attachFileValue 게시물 첨부파일이 노출되는 플랫폼
     * @return 특정 게시물 첨부파일 엔티티(Nullable)
     */
    BdtArticleAttachFile findBdtArticleAttachFileByArticleAndAttachFileValue(BdtArticle article, AttachFileValue attachFileValue);

    /**
     * 특정 게시물 삭제
     * @param articleNo 삭제할 게시물 고유번호
     */
    void delete(Integer articleNo);
}