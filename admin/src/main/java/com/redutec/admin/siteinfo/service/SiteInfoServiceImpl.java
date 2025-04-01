package com.redutec.admin.siteinfo.service;

import com.redutec.admin.siteinfo.dto.SiteInfoDto;
import com.redutec.core.entity.CmtConfigurationGeneral;
import com.redutec.core.repository.CmtConfigurationGeneralRepository;
import com.redutec.core.specification.CmtConfigurationGeneralSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SiteInfoServiceImpl implements SiteInfoService {
    private final CmtConfigurationGeneralRepository configurationGeneralRepository;
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
        return SiteInfoDto.SiteInfoResponse.fromEntity(
                configurationGeneralRepository.save(CmtConfigurationGeneral.builder()
                        .configurationKey(createSiteInfoRequest.getConfigurationKey())
                        .configurationCategoryKey(createSiteInfoRequest.getConfigurationCategoryKey())
                        .configurationCategoryName(createSiteInfoRequest.getConfigurationCategoryName())
                        .configurationName(createSiteInfoRequest.getConfigurationName())
                        .configurationContent(createSiteInfoRequest.getConfigurationContent())
                        .useYn(createSiteInfoRequest.getUseYn())
                        .description(createSiteInfoRequest.getDescription())
                        .build()));
    }

    /**
     * 조건에 맞는 사이트 설정 목록 조회
     */
    @Override
    @Transactional(readOnly = true)
    public SiteInfoDto.SiteInfoPageResponse find(
            SiteInfoDto.FindSiteInfoRequest findSiteInfoRequest
    ) {
        // 조건에 맞는 사이트 설정 페이징 목록 조회
        Page<CmtConfigurationGeneral> siteInfoPage = configurationGeneralRepository.findAll(
                CmtConfigurationGeneralSpecification.findWith(findSiteInfoRequest.toCriteria()),
                (findSiteInfoRequest.getPage() != null && findSiteInfoRequest.getSize() != null)
                        ? PageRequest.of(findSiteInfoRequest.getPage(), findSiteInfoRequest.getSize())
                        : Pageable.unpaged()
        );
        // 조회된 엔티티들을 응답 DTO로 변환 후 리턴
        List<SiteInfoDto.SiteInfoResponse> siteInfoList = siteInfoPage.getContent().stream()
                .map(SiteInfoDto.SiteInfoResponse::fromEntity)
                .collect(Collectors.toList());
        return SiteInfoDto.SiteInfoPageResponse.builder()
                .siteInfoList(siteInfoList)
                .totalElements(siteInfoPage.getTotalElements())
                .totalPages(siteInfoPage.getTotalPages())
                .build();
    }

    /**
     * 특정 사이트 설정 상세 조회
     */
    @Override
    @Transactional(readOnly = true)
    public SiteInfoDto.SiteInfoResponse findByConfigurationKey(String configurationKey) {
        return SiteInfoDto.SiteInfoResponse.fromEntity(getSiteInfo(configurationKey));
    }

    @Override
    public CmtConfigurationGeneral getSiteInfo(String configurationKey) {
        return cmtConfigurationGeneralRepository.findById(configurationKey)
                .orElseThrow(() -> new EntityNotFoundException("No such configuration general with key: " + configurationKey));
    }

    /**
     * 사이트 설정 수정
     */
    @Override
    @Transactional
    public void update(
            String configurationKey,
            SiteInfoDto.UpdateSiteInfoRequest updateSiteInfoRequest
    ) {
        // 수정하려는 사이트 설정이 존재하는지 조회
        CmtConfigurationGeneral siteInfo = getSiteInfo(configurationKey);
        // 수정할 데이터를 엔티티에 설정 후 저장
        siteInfo.updateCmtConfigurationGeneral(
                updateSiteInfoRequest.getConfigurationKey(),
                updateSiteInfoRequest.getConfigurationCategoryKey(),
                updateSiteInfoRequest.getConfigurationCategoryName(),
                updateSiteInfoRequest.getConfigurationName(),
                updateSiteInfoRequest.getConfigurationContent(),
                updateSiteInfoRequest.getUseYn(),
                updateSiteInfoRequest.getDescription()
        );
        configurationGeneralRepository.save(siteInfo);
    }

    /**
     * 사이트 설정 삭제
     */
    @Override
    @Transactional
    public void delete(String configurationKey) {
        configurationGeneralRepository.delete(getSiteInfo(configurationKey));
    }
}