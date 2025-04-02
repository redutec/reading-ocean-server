package com.redutec.admin.article.service;

import com.redutec.admin.article.dto.ArticleDto;
import com.redutec.core.entity.BdtArticle;
import com.redutec.core.entity.BdtArticleAttachFile;
import com.redutec.core.entity.BdtArticleDisplay;
import com.redutec.core.meta.AttachFileValue;
import org.springframework.data.domain.Page;

public interface ArticleService {
    /**
     * 게시물(배너, FAQ, 팝업 등) 등록
     * @param article 게시물(배너, FAQ, 팝업 등) 등록 정보를 담은 엔티티
     * @param articleDisplay 게시물 노출 관련 등록 정보를 담은 엔티티
     * @param articleAttachFile 게시물 첨부 파일 등록 정보를 담은 엔티티
     */
    void create(
            BdtArticle article,
            BdtArticleDisplay articleDisplay,
            BdtArticleAttachFile articleAttachFile
    );

    /**
     * 조건에 맞는 게시물(배너, FAQ, 팝업 등) 목록 조회
     * @param findArticleRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 게시물(배너, FAQ, 팝업 등) 엔티티 페이징 객체
     */
    Page<BdtArticle> find(ArticleDto.FindArticleRequest findArticleRequest);

    /**
     * 특정 게시물(배너, FAQ, 팝업 등) 엔티티 조회
     * @param articleNo 게시물 고유번호
     * @return 특정 게시물 엔티티 객체
     */
    BdtArticle getBdtArticle(Integer articleNo);

    /**
     * 특정 게시물 노출 설정(배너, FAQ, 팝업 등을 포함) 엔티티 조회
     * @param articleNo 게시물 노출 설정 고유번호
     * @return 특정 게시물 노출 설정 엔티티 객체
     */
    BdtArticleDisplay getBdtArticleDisplay(Integer articleNo);

    /**
     * 특정 게시물 첨부파일(배너, FAQ, 팝업 등을 포함) 엔티티 조회
     * @param article 게시물 엔티티
     * @param attachFileValue 사용하는 기기
     * @return 특정 게시물 첨부파일 엔티티 객체
     */
    BdtArticleAttachFile getBdtArticleAttachFileByArticleAndAttachFileValue(
            BdtArticle article,
            AttachFileValue attachFileValue
    );

    /**
     * 특정 게시물(배너, FAQ, 팝업 등을 포함) 수정
     * @param article 게시물(배너, FAQ, 팝업 등) 수정 정보를 담은 엔티티
     * @param articleDisplay 게시물 노출 관련 수정 정보를 담은 엔티티
     * @param articleAttachFile 게시물 첨부 파일 수정 정보를 담은 엔티티
     */
    void update(
            BdtArticle article,
            BdtArticleDisplay articleDisplay,
            BdtArticleAttachFile articleAttachFile
    );

    /**
     * 특정 게시물 삭제
     * @param article 삭제할 게시물(배너, FAQ, 팝업 등) 엔티티
     * @param articleDisplay 삭제할 게시물 노출 정보 엔티티
     * @param articleAttachFile 삭제할 게시물 첨부 파일 엔티티
     */
    void delete(
            BdtArticle article,
            BdtArticleDisplay articleDisplay,
            BdtArticleAttachFile articleAttachFile
    );
}