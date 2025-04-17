package com.redutec.admin.v1.siteinfo.service;

import com.redutec.admin.v1.siteinfo.dto.SiteInfoDto;
import com.redutec.admin.v1.siteinfo.mapper.SiteInfoMapper;
import com.redutec.core.entity.v1.CmtConfigurationGeneral;
import com.redutec.core.repository.v1.CmtConfigurationGeneralRepository;
import com.redutec.core.specification.v1.CmtConfigurationGeneralSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SiteInfoServiceImpl implements SiteInfoService {
    private final SiteInfoMapper siteInfoMapper;
    private final CmtConfigurationGeneralRepository cmtConfigurationGeneralRepository;

    /**
     * 사이트 설정 등록
     * @param createSiteInfoRequest 사이트 설정 등록 요청 객체
     * @return 등록한 사이트 설정 정보
     */
    @Override
    @Transactional
    public SiteInfoDto.SiteInfoResponse create(
            SiteInfoDto.CreateSiteInfoRequest createSiteInfoRequest
    ) {
        return siteInfoMapper.toResponseDto(cmtConfigurationGeneralRepository.save(siteInfoMapper.toEntity(createSiteInfoRequest)));
    }

    /**
     * 조건에 맞는 사이트 설정 목록 조회
     * @param findSiteInfoRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 사이트 설정 목록 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public SiteInfoDto.SiteInfoPageResponse find(
            SiteInfoDto.FindSiteInfoRequest findSiteInfoRequest
    ) {
        return siteInfoMapper.toPageResponseDto(cmtConfigurationGeneralRepository.findAll(
                CmtConfigurationGeneralSpecification.findWith(siteInfoMapper.toCriteria(findSiteInfoRequest)),
                (findSiteInfoRequest.page() != null && findSiteInfoRequest.size() != null)
                        ? PageRequest.of(findSiteInfoRequest.page(), findSiteInfoRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 사이트 설정 상세 조회
     * @param configurationKey 사이트 설정 고유 키
     * @return 특정 사이트 설정 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public SiteInfoDto.SiteInfoResponse findByConfigurationKey(
            String configurationKey
    ) {
        return siteInfoMapper.toResponseDto(getSiteInfo(configurationKey));
    }

    /**
     * 특정 사이트 설정 엔티티 조회
     * @param configurationKey 사이트 설정 고유번호
     * @return 특정 사이트 설정 엔티티 객체
     */
    @Override
    public CmtConfigurationGeneral getSiteInfo(
            String configurationKey
    ) {
        return cmtConfigurationGeneralRepository.findById(configurationKey)
                .orElseThrow(() -> new EntityNotFoundException("No such configuration general with key: " + configurationKey));
    }

    /**
     * 사이트 설정 수정
     * @param configurationKey 수정할 사이트 설정 고유 키
     * @param updateSiteInfoRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            String configurationKey,
            SiteInfoDto.UpdateSiteInfoRequest updateSiteInfoRequest
    ) {
        // 수정하려는 사이트 설정이 존재하는지 조회
        CmtConfigurationGeneral siteInfo = getSiteInfo(configurationKey);
        // UPDATE 도메인 메서드로 변환
        siteInfo.updateCmtConfigurationGeneral(
                updateSiteInfoRequest.configurationKey(),
                updateSiteInfoRequest.configurationCategoryKey(),
                updateSiteInfoRequest.configurationCategoryName(),
                updateSiteInfoRequest.configurationName(),
                updateSiteInfoRequest.configurationContent(),
                updateSiteInfoRequest.useYn(),
                updateSiteInfoRequest.description()
        );
        // 사이트 설정 엔티티 UPDATE
        cmtConfigurationGeneralRepository.save(siteInfo);
    }

    /**
     * 사이트 설정 삭제
     */
    @Override
    @Transactional
    public void delete(
            String configurationKey
    ) {
        cmtConfigurationGeneralRepository.delete(getSiteInfo(configurationKey));
    }
}