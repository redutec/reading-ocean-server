package com.redutec.admin.article.service;

import com.redutec.admin.article.dto.BannerDto;
import org.springframework.web.multipart.MultipartFile;

public interface BannerService {
    /**
     * 배너 등록
     * @param createBannerRequest 배너 등록 정보를 담은 DTO
     */
    void create(
            BannerDto.CreateBannerRequest createBannerRequest,
            MultipartFile pcBannerImageFile,
            MultipartFile mobileBannerImageFile
    );

    /**
     * 조건에 맞는 배너 목록 조회
     * @param findBannerRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 배너 목록 응답 객체
     */
    BannerDto.BannerPageResponse find(BannerDto.FindBannerRequest findBannerRequest);

    /**
     * 특정 배너 상세 조회
     * @param bannerNo 배너 고유번호
     * @return 특정 배너 응답 객체
     */
    BannerDto.BannerResponse findByBannerNo(Integer bannerNo);

    /**
     * 특정 배너 수정
     * @param bannerNo 수정할 배너 고유번호
     * @param updateBannerRequest 수정할 정보를 담은 DTO
     */
    void update(
            Integer bannerNo,
            BannerDto.UpdateBannerRequest updateBannerRequest,
            MultipartFile pcBannerImageFile,
            MultipartFile mobileBannerImageFile
    );
}