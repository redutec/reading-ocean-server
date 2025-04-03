package com.redutec.admin.article.service.banner;

import com.redutec.admin.article.dto.ArticleDto;
import org.springframework.web.multipart.MultipartFile;

public interface BannerService {
    /**
     * 배너 등록
     * @param createBannerRequest 배너 등록 정보를 담은 DTO
     */
    void create(
            ArticleDto.CreateBannerRequest createBannerRequest,
            MultipartFile pcArticleAttachFile,
            MultipartFile mobileArticleAttachFile
    );

    /**
     * 조건에 맞는 배너 목록 조회
     * @param findBannerRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 배너 목록 응답 객체
     */
    ArticleDto.BannerPageResponse find(ArticleDto.FindArticleRequest findBannerRequest);

    /**
     * 특정 배너 상세 조회
     * @param bannerNo 배너 고유번호
     * @return 특정 배너 응답 객체
     */
    ArticleDto.BannerResponse findByBannerNo(Integer bannerNo);

    /**
     * 특정 배너 수정
     * @param bannerNo 수정할 배너 고유번호
     * @param updateBannerRequest 수정할 정보를 담은 DTO
     */
    void update(Integer bannerNo, ArticleDto.UpdateBannerRequest updateBannerRequest);

    /**
     * 특정 배너 삭제
     * @param bannerNo 삭제할 배너 고유번호
     */
    void delete(Integer bannerNo);
}