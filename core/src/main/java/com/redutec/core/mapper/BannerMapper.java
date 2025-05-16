package com.redutec.core.mapper;

import com.redutec.core.criteria.BannerCriteria;
import com.redutec.core.dto.BannerDto;
import com.redutec.core.entity.Banner;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class BannerMapper {
    /**
     * CreateBannerRequest DTO를 기반으로 Banner 등록 엔티티를 생성합니다.
     *
     * @param createBannerRequest Banner 등록에 필요한 데이터를 담은 DTO
     * @param attachedFileName 첨부 파일명
     * @return 등록할 Banner 엔티티
     */
    public Banner toCreateEntity(
            BannerDto.CreateBannerRequest createBannerRequest,
            String attachedFileName
    ) {
        return Banner.builder()
                .domain(createBannerRequest.domain())
                .title(createBannerRequest.title())
                .content(createBannerRequest.content())
                .linkUrl(createBannerRequest.linkUrl())
                .attachedFileName(attachedFileName)
                .priority(createBannerRequest.priority())
                .visible(createBannerRequest.visible())
                .visibleStartAt(createBannerRequest.visibleStartAt())
                .visibleEndAt(createBannerRequest.visibleEndAt())
                .build();
    }

    /**
     * UpdateBannerRequest DTO를 기반으로 Banner 수정 엔티티를 생성합니다.
     *
     * @param banner 수정할 Banner 엔티티
     * @param updateBannerRequest Banner 수정에 필요한 데이터를 담은 DTO
     * @param attachedFileName 첨부 파일명
     * @return 생성된 Banner 수정 엔티티
     */
    public Banner toUpdateEntity(
            Banner banner,
            BannerDto.UpdateBannerRequest updateBannerRequest,
            String attachedFileName
    ) {
        return Banner.builder()
                .id(banner.getId())
                .domain(Optional.ofNullable(updateBannerRequest.domain()).orElse(banner.getDomain()))
                .title(Optional.ofNullable(updateBannerRequest.title()).orElse(banner.getTitle()))
                .content(Optional.ofNullable(updateBannerRequest.content()).orElse(banner.getContent()))
                .linkUrl(Optional.ofNullable(updateBannerRequest.linkUrl()).orElse(banner.getLinkUrl()))
                .attachedFileName(Optional.ofNullable(attachedFileName).orElse(banner.getAttachedFileName()))
                .priority(Optional.ofNullable(updateBannerRequest.priority()).orElse(banner.getPriority()))
                .visible(Optional.ofNullable(updateBannerRequest.visible()).orElse(banner.getVisible()))
                .visibleStartAt(Optional.ofNullable(updateBannerRequest.visibleStartAt()).orElse(banner.getVisibleStartAt()))
                .visibleEndAt(Optional.ofNullable(updateBannerRequest.visibleEndAt()).orElse(banner.getVisibleEndAt()))
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindBannerRequest 객체를 기반으로
     * BannerCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 배너 검색 조건을 구성할 때 사용됩니다.
     *
     * @param findBannerRequest 배너 조회 요청 객체
     * @return 해당 요청의 필드를 이용해 생성된 BannerCriteria 객체
     */
    public BannerCriteria toCriteria(BannerDto.FindBannerRequest findBannerRequest) {
        return new BannerCriteria(
                findBannerRequest.bannerIds(),
                findBannerRequest.domains(),
                findBannerRequest.title(),
                findBannerRequest.content(),
                findBannerRequest.visible()
        );
    }

    /**
     * Banner 엔티티를 기반으로 응답용 BannerResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param banner 변환할 Banner 엔티티(null 가능)
     * @return Banner 엔티티의 데이터를 담은 BannerResponse DTO, banner가 null이면 null 반환
     */
    public BannerDto.BannerResponse toResponseDto(Banner banner) {
        return Optional.ofNullable(banner)
                .map(b -> new BannerDto.BannerResponse(
                        b.getId(),
                        b.getDomain(),
                        b.getTitle(),
                        b.getContent(),
                        b.getLinkUrl(),
                        b.getAttachedFileName(),
                        b.getPriority(),
                        b.getVisible(),
                        b.getVisibleStartAt(),
                        b.getVisibleEndAt(),
                        b.getCreatedAt(),
                        b.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 Banner 엔티티 목록을 BannerPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param bannerPage Page 형태로 조회된 Banner 엔티티 목록 (null 가능)
     * @return Banner 엔티티 리스트와 페이지 정보를 담은 BannerPageResponse DTO, bannerPage가 null이면 null 반환
     */
    public BannerDto.BannerPageResponse toPageResponseDto(Page<Banner> bannerPage) {
        return Optional.ofNullable(bannerPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new BannerDto.BannerPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}