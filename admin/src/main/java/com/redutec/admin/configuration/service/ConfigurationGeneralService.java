package com.redutec.admin.configuration.service;

import com.redutec.admin.configuration.dto.ConfigurationGeneralDto;
import com.redutec.core.entity.CmtConfigurationGeneral;

public interface ConfigurationGeneralService {
    /**
     * 사이트 설정 등록
     * @param configurationGeneralRequest 사이트 설정 등록 정보를 담은 DTO
     * @return 등록된 사이트 설정 정보 응답 객체
     */
    ConfigurationGeneralDto.ConfigurationGeneralResponse create(ConfigurationGeneralDto.CreateConfigurationGeneralRequest configurationGeneralRequest);

    /**
     * 조건에 맞는 사이트 설정 목록 조회
     * @param findConfigurationGeneralRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 사이트 설정 목록 응답 객체
     */
    ConfigurationGeneralDto.ConfigurationGeneralPageResponse find(ConfigurationGeneralDto.FindConfigurationGeneralRequest findConfigurationGeneralRequest);

    /**
     * 특정 사이트 설정 상세 조회
     * @param configurationKey 사이트 설정 고유 키
     * @return 특정 사이트 설정 응답 객체
     */
    ConfigurationGeneralDto.ConfigurationGeneralResponse findByConfigurationKey(String configurationKey);

    /**
     * 특정 사이트 설정 엔티티 조회
     * @param configurationKey 사이트 설정 고유번호
     * @return 특정 사이트 설정 엔티티 객체
     */
    CmtConfigurationGeneral getCmtConfigurationGeneral(String configurationKey);

    /**
     * 사이트 설정 수정
     * @param configurationKey 수정할 사이트 설정 고유 키
     * @param updateConfigurationGeneralRequest 수정할 정보를 담은 DTO
     */
    void update(String configurationKey, ConfigurationGeneralDto.UpdateConfigurationGeneralRequest updateConfigurationGeneralRequest);

    /**
     * 사이트 설정 삭제
     * @param configurationKey 삭제할 사이트 설정 고유 키
     */
    void delete(String configurationKey);
}