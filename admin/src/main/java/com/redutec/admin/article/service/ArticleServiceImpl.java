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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final BdtArticleRepository bdtArticleRepository;
    private final BdtArticleAttachFileRepository bdtArticleAttachFileRepository;
    private final BdtArticleDisplayRepository bdtArticleDisplayRepository;

    /**
     * 게시물(배너, FAQ, 팝업 등) 등록 또는 수정
     * @param article 게시물(배너, FAQ, 팝업 등) 등록 또는 수정 정보를 담은 엔티티
     * @param articleDisplay 게시물 노출 관련 등록 또는 수정 정보를 담은 엔티티
     */
    @Override
    @Transactional
    public void saveArticleAndDisplay(BdtArticle article, BdtArticleDisplay articleDisplay) {
        bdtArticleRepository.save(article);
        bdtArticleDisplayRepository.save(articleDisplay);
    }

    /**
     * 게시물(배너, FAQ, 팝업 등) 첨부 파일 정보 등록 또는 수정
     * @param articleAttachFile 게시물 첨부 파일 등록 또는 수정 정보를 담은 엔티티
     */
    @Override
    @Transactional
    public void saveArticleAttachFile(BdtArticleAttachFile articleAttachFile) {
        bdtArticleAttachFileRepository.save(articleAttachFile);
    }

    /**
     * 조건에 맞는 게시물(배너, FAQ, 팝업 등) 엔티티(페이징) 조회
     * @param findArticleRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 게시물(배너, FAQ, 팝업 등) 엔티티(페이징) 객체
     */
    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public BdtArticle getBdtArticle(Integer articleNo) {
        return bdtArticleRepository.findById(articleNo)
                .orElseThrow(() -> new EntityNotFoundException("No such article"));
    }

    /**
     * 특정 게시물 노출 설정(배너, FAQ, 팝업 등) 엔티티 조회
     * @param articleNo 게시물 노출 설정 고유번호
     * @return 특정 게시물 노출 설정 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public BdtArticleDisplay findBdtArticleDisplay(Integer articleNo) {
        return bdtArticleDisplayRepository.findById(articleNo)
                .orElse(null);
    }

    /**
     * 특정 게시물 첨부파일(배너, FAQ, 팝업 등) 엔티티 조회
     * @param article 게시물 엔티티
     * @param attachFileValue 사용하는 기기
     * @return 특정 게시물 첨부파일 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public BdtArticleAttachFile findBdtArticleAttachFileByArticleAndAttachFileValue(
            BdtArticle article,
            AttachFileValue attachFileValue
    ) {
        return bdtArticleAttachFileRepository.findByArticleAndAttachFileValue(article, attachFileValue)
                .orElse(null);
    }

    /**
     * 특정 게시물 삭제
     * @param article 삭제할 게시물(배너, FAQ, 팝업 등) 엔티티
     * @param articleDisplay 삭제할 게시물 노출 정보 엔티티
     * @param articleAttachFileList 삭제할 게시물 첨부 파일 엔티티 목록
     */
    @Override
    @Transactional
    public void delete(
            BdtArticle article,
            BdtArticleDisplay articleDisplay,
            List<BdtArticleAttachFile> articleAttachFileList
    ) {
        bdtArticleAttachFileRepository.deleteAll(articleAttachFileList);
        bdtArticleDisplayRepository.delete(articleDisplay);
        bdtArticleRepository.delete(article);
    }
}