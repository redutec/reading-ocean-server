package com.redutec.admin.article.service;

import com.redutec.admin.article.dto.ArticleDto;
import com.redutec.core.entity.BdtArticle;
import com.redutec.core.entity.BdtArticleAttachFile;
import com.redutec.core.entity.BdtArticleDisplay;
import com.redutec.core.meta.AttachFileValue;
import com.redutec.core.repository.BdtArticleAttachFileRepository;
import com.redutec.core.repository.BdtArticleDisplayRepository;
import com.redutec.core.repository.BdtArticleRepository;
import com.redutec.core.specification.BdtArticleSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final BdtArticleRepository bdtArticleRepository;
    private final BdtArticleAttachFileRepository bdtArticleAttachFileRepository;
    private final BdtArticleDisplayRepository bdtArticleDisplayRepository;

    /**
     * 게시물(배너, FAQ, 팝업 등) 등록
     * @param article 게시물(배너, FAQ, 팝업 등) 등록 정보를 담은 엔티티
     * @param articleDisplay 게시물 노출 관련 등록 정보를 담은 엔티티
     * @param articleAttachFile 게시물 첨부 파일 등록 정보를 담은 엔티티
     */
    @Override
    public void create(
            BdtArticle article,
            BdtArticleDisplay articleDisplay,
            BdtArticleAttachFile articleAttachFile
    ) {
        bdtArticleRepository.save(article);
        bdtArticleDisplayRepository.save(articleDisplay);
        bdtArticleAttachFileRepository.save(articleAttachFile);
    }

    /**
     * 조건에 맞는 게시물(배너, FAQ, 팝업 등) 엔티티(페이징) 조회
     * @param findArticleRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 게시물(배너, FAQ, 팝업 등) 엔티티(페이징) 객체
     */
    @Override
    public Page<BdtArticle> find(ArticleDto.FindArticleRequest findArticleRequest) {
        // 게시물 엔티티 조회
        return bdtArticleRepository.findAll(
                BdtArticleSpecification.findWith(findArticleRequest.toCriteria()),
                (findArticleRequest.getPage() != null && findArticleRequest.getSize() != null)
                        ? PageRequest.of(findArticleRequest.getPage(), findArticleRequest.getSize())
                        : Pageable.unpaged());
    }

    /**
     * 특정 게시물(배너, FAQ, 팝업 등) 엔티티 조회
     * @param articleNo 게시물 고유번호
     * @return 특정 게시물 엔티티 객체
     */
    @Override
    public BdtArticle getBdtArticle(Integer articleNo) {
        return bdtArticleRepository.findById(articleNo)
                .orElseThrow(() -> new EntityNotFoundException("No such article"));
    }

    /**
     * 특정 게시물 노출 설정(배너, FAQ, 팝업 등을 포함) 엔티티 조회
     * @param articleNo 게시물 노출 설정 고유번호
     * @return 특정 게시물 노출 설정 엔티티 객체
     */
    @Override
    public BdtArticleDisplay getBdtArticleDisplay(Integer articleNo) {
        return bdtArticleDisplayRepository.findById(articleNo)
                .orElseThrow(() -> new EntityNotFoundException("No such articleDisplay"));
    }

    /**
     * 특정 게시물 첨부파일(배너, FAQ, 팝업 등을 포함) 엔티티 조회
     * @param article 게시물 엔티티
     * @param attachFileValue 사용하는 기기
     * @return 특정 게시물 첨부파일 엔티티 객체
     */
    @Override
    public BdtArticleAttachFile getBdtArticleAttachFileByArticleAndAttachFileValue(
            BdtArticle article,
            AttachFileValue attachFileValue
    ) {
        return bdtArticleAttachFileRepository.findByArticleAndAttachFileValue(article, attachFileValue.getCode())
                .orElseThrow(() -> new EntityNotFoundException("No such articleAttachFile"));
    }

    /**
     * 특정 게시물(배너, FAQ, 팝업 등을 포함) 수정
     * @param article 게시물(배너, FAQ, 팝업 등) 수정 정보를 담은 엔티티
     * @param articleDisplay 게시물 노출 관련 수정 정보를 담은 엔티티
     * @param articleAttachFile 게시물 첨부 파일 수정 정보를 담은 엔티티
     */
    @Override
    public void update(
            BdtArticle article,
            BdtArticleDisplay articleDisplay,
            BdtArticleAttachFile articleAttachFile
    ) {
        bdtArticleRepository.save(article);
        bdtArticleDisplayRepository.save(articleDisplay);
        bdtArticleAttachFileRepository.save(articleAttachFile);
    }

    /**
     * 특정 게시물 삭제
     * @param article 삭제할 게시물(배너, FAQ, 팝업 등) 엔티티
     * @param articleDisplay 삭제할 게시물 노출 정보 엔티티
     * @param articleAttachFile 삭제할 게시물 첨부 파일 엔티티
     */
    @Override
    public void delete(
            BdtArticle article,
            BdtArticleDisplay articleDisplay,
            BdtArticleAttachFile articleAttachFile
    ) {
        bdtArticleAttachFileRepository.delete(articleAttachFile);
        bdtArticleDisplayRepository.delete(articleDisplay);
        bdtArticleRepository.delete(article);
    }
}