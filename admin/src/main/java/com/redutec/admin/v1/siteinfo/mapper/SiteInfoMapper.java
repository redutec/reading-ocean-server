package com.redutec.admin.v1.siteinfo.mapper;

import com.redutec.admin.v1.siteinfo.dto.SiteInfoDto;
import com.redutec.core.criteria.CmtConfigurationGeneralCriteria;
import com.redutec.core.entity.v1.CmtConfigurationGeneral;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class SiteInfoMapper {
    /**
     * CreateSiteInfoRequest DTO를 기반으로 CmtConfigurationGeneral 엔티티를 생성합니다.
     * @param createSiteInfoRequest 사이트 설정 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 CmtConfigurationGeneral 엔티티
     */
    public CmtConfigurationGeneral toEntity(
            SiteInfoDto.CreateSiteInfoRequest createSiteInfoRequest
    ) {
        return CmtConfigurationGeneral.builder()
                .configurationKey(createSiteInfoRequest.configurationKey())
                .configurationCategoryKey(createSiteInfoRequest.configurationCategoryKey())
                .configurationCategoryName(createSiteInfoRequest.configurationCategoryName())
                .configurationName(createSiteInfoRequest.configurationName())
                .configurationContent(createSiteInfoRequest.configurationContent())
                .useYn(createSiteInfoRequest.useYn())
                .description(createSiteInfoRequest.description())
                .build();
    }

    /**
     * FindSiteInfoRequest DTO를 기반으로 검색 조건 객체인 CmtConfigurationGeneralCriteria를 생성합니다.
     * 내부 검색 로직에서 사이트 설정 검색 조건을 구성할 때 사용됩니다.
     * @param findSiteInfoRequest 사이트 설정 조회 조건을 담은 DTO
     * @return 해당 요청의 필드를 이용해 생성된 CmtConfigurationGeneralCriteria 객체
     */
    public CmtConfigurationGeneralCriteria toCriteria(
            SiteInfoDto.FindSiteInfoRequest findSiteInfoRequest
    ) {
        return CmtConfigurationGeneralCriteria.builder()
                .configurationKeyList(findSiteInfoRequest.configurationKeyList())
                .configurationCategoryKey(findSiteInfoRequest.configurationCategoryKey())
                .configurationCategoryName(findSiteInfoRequest.configurationCategoryName())
                .configurationName(findSiteInfoRequest.configurationName())
                .configurationContent(findSiteInfoRequest.configurationContent())
                .useYn(findSiteInfoRequest.useYn())
                .description(findSiteInfoRequest.description())
                .build();
    }

    /**
     * SiteInfo 엔티티를 기반으로 응답용 SiteInfoResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     * @param cmtConfigurationGeneral 변환할 엔티티 (null 가능)
     * @return SiteInfo 엔티티의 데이터를 담은 SiteInfoResponse DTO, company가 null이면 null 반환
     */
    public SiteInfoDto.SiteInfoResponse toResponseDto(
            CmtConfigurationGeneral cmtConfigurationGeneral
    ) {
        return Optional.ofNullable(cmtConfigurationGeneral)
                .map(c -> new SiteInfoDto.SiteInfoResponse(
                        c.getConfigurationKey(),
                        c.getConfigurationCategoryKey(),
                        c.getConfigurationCategoryName(),
                        c.getConfigurationName(),
                        c.getConfigurationContent(),
                        c.getUseYn(),
                        c.getRegisterDatetime(),
                        c.getModifyDatetime(),
                        c.getDescription()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 엔티티 목록을 SiteInfoPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     * @param cmtConfigurationGeneralPage Page 형태로 조회된 엔티티 목록 (null 가능)
     * @return SiteInfo 엔티티 리스트와 페이지 정보를 담은 SiteInfoPageResponse DTO, cmtConfigurationGeneralPage가 null이면 null 반환
     */
    public SiteInfoDto.SiteInfoPageResponse toPageResponseDto(
            Page<CmtConfigurationGeneral> cmtConfigurationGeneralPage
    ) {
        return Optional.ofNullable(cmtConfigurationGeneralPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new SiteInfoDto.SiteInfoPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}