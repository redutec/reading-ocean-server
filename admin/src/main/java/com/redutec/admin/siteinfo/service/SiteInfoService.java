package com.redutec.admin.siteinfo.service;

import com.redutec.admin.siteinfo.dto.SiteInfoDto;
import com.redutec.core.entity.CmtConfigurationGeneral;

public interface SiteInfoService {
    /**
     * 사이트 설정 등록
     * @param createSiteInfoRequest 사이트 설정 등록 정보를 담은 DTO
     * @return 등록된 사이트 설정 정보 응답 객체
     */
    SiteInfoDto.SiteInfoResponse create(SiteInfoDto.CreateSiteInfoRequest createSiteInfoRequest);

    /**
     * 조건에 맞는 사이트 설정 목록 조회
     * @param findSiteInfoRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 사이트 설정 목록 응답 객체
     */
    SiteInfoDto.SiteInfoPageResponse find(SiteInfoDto.FindSiteInfoRequest findSiteInfoRequest);

    /**
     * 특정 사이트 설정 상세 조회
     * @param configurationKey 사이트 설정 고유 키
     * @return 특정 사이트 설정 응답 객체
     */
    SiteInfoDto.SiteInfoResponse findByConfigurationKey(String configurationKey);

    /**
     * 특정 사이트 설정 엔티티 조회
     * @param configurationKey 사이트 설정 고유번호
     * @return 특정 사이트 설정 엔티티 객체
     */
    CmtConfigurationGeneral getSiteInfo(String configurationKey);

    /**
     * 사이트 설정 수정
     * @param configurationKey 수정할 사이트 설정 고유 키
     * @param updateSiteInfoRequest 수정할 정보를 담은 DTO
     */
    void update(String configurationKey, SiteInfoDto.UpdateSiteInfoRequest updateSiteInfoRequest);

    /**
     * 사이트 설정 삭제
     * @param configurationKey 삭제할 사이트 설정 고유 키
     */
    void delete(String configurationKey);
}