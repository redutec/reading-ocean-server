package com.redutec.admin.banner.service;

import com.redutec.core.dto.BannerDto;
import com.redutec.core.entity.Banner;

public interface BannerService {
    /**
     * 배너 등록
     * @param createBannerRequest 배너 등록 정보를 담은 DTO
     * @return 등록된 배너 정보
     */
    BannerDto.BannerResponse create(BannerDto.CreateBannerRequest createBannerRequest);

    /**
     * 조건에 맞는 배너 목록 조회
     * @param findBannerRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 배너 목록 및 페이징 정보
     */
    BannerDto.BannerPageResponse find(BannerDto.FindBannerRequest findBannerRequest);

    /**
     * 특정 배너 조회
     * @param bannerId 배너 고유번호
     * @return 특정 배너 응답 객체
     */
    BannerDto.BannerResponse findById(Long bannerId);

    /**
     * 특정 배너 엔티티 조회
     * @param bannerId 배너 고유번호
     * @return 특정 배너 엔티티 객체
     */
    Banner getBanner(Long bannerId);

    /**
     * 특정 배너 수정
     * @param bannerId 수정할 배너의 ID
     * @param updateBannerRequest 배너 수정 요청 객체
     */
    void update(Long bannerId, BannerDto.UpdateBannerRequest updateBannerRequest);

    /**
     * 특정 배너 삭제
     * @param bannerId 삭제할 배너의 ID
     */
    void delete(Long bannerId);
}