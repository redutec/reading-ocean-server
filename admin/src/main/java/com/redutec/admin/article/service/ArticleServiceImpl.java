package com.redutec.admin.article.service;

import com.redutec.core.entity.BdtArticle;
import com.redutec.core.entity.BdtArticleAttachFile;
import com.redutec.core.entity.BdtArticleDisplay;
import com.redutec.core.meta.AttachFileValue;
import com.redutec.core.repository.BdtArticleAttachFileRepository;
import com.redutec.core.repository.BdtArticleDisplayRepository;
import com.redutec.core.repository.BdtArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final BdtArticleRepository bdtArticleRepository;
    private final BdtArticleDisplayRepository bdtArticleDisplayRepository;
    private final BdtArticleAttachFileRepository bdtArticleAttachFileRepository;

    /**
     * 특정 게시물 엔티티 조회
     * @param articleNo 게시물 고유번호
     * @return 특정 게시물 엔티티
     */
    @Override
    public BdtArticle getBdtArticleByArticleNo(
            Integer articleNo
    ) {
        return bdtArticleRepository.findById(articleNo).orElseThrow(() -> new EntityNotFoundException("No such article"));
    }

    /**
     * 특정 게시물 노출정보 엔티티 조회
     * @param article 게시물 엔티티
     * @return 특정 게시물 노출정보 엔티티(Nullable)
     */
    @Override
    public BdtArticleDisplay findBdtArticleDisplayByArticle(
            BdtArticle article
    ) {
        return bdtArticleDisplayRepository.findByArticle(article).orElse(null);
    }

    /**
     * 특정 게시물 첨부파일 엔티티 조회
     * @param article 게시물 엔티티
     * @param attachFileValue 게시물 첨부파일이 노출되는 플랫폼
     * @return 특정 게시물 첨부파일 엔티티(Nullable)
     */
    @Override
    public BdtArticleAttachFile findBdtArticleAttachFileByArticleAndAttachFileValue(
            BdtArticle article,
            AttachFileValue attachFileValue
    ) {
        return bdtArticleAttachFileRepository.findByArticleAndAttachFileValue(article, attachFileValue).orElse(null);
    }

    /**
     * 특정 게시물 삭제
     * @param articleNo 삭제할 게시물 고유번호
     */
    @Override
    public void delete(
            Integer articleNo
    ) {
        BdtArticle article = getBdtArticleByArticleNo(articleNo);
        BdtArticleDisplay articleDisplay = findBdtArticleDisplayByArticle(article);
        bdtArticleRepository.delete(article);
        bdtArticleDisplayRepository.delete(articleDisplay);
        bdtArticleAttachFileRepository.deleteByArticle(article);
    }
}