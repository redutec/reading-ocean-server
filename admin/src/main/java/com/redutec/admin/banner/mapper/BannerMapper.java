package com.redutec.admin.banner.mapper;

import com.redutec.admin.banner.dto.BannerDto;
import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.criteria.BannerCriteria;
import com.redutec.core.entity.Banner;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class BannerMapper {
    private final FileUtil fileUtil;

    /**
     * CreateBannerRequest DTO를 기반으로 Banner 엔티티를 생성합니다.
     *
     * @param createBannerRequest 배너 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 Banner 엔티티
     */
    public Banner toEntity(BannerDto.CreateBannerRequest createBannerRequest) {
        // 첨부 파일이 존재하는 경우 파일을 업로드하고 파일명을 가져오기(파일이 없으면 파일명은 null)
        String imageFileName = Optional.ofNullable(createBannerRequest.imageFile())
                .filter(imageFile -> !imageFile.isEmpty())
                .map(imageFile -> {
                    FileUploadResult result = fileUtil.uploadFile(imageFile, "/banner");
                    return Paths.get(result.filePath()).getFileName().toString();
                })
                .orElse(null);
        // Banner 엔티티 Build
        return Banner.builder()
                .domain(createBannerRequest.domain())
                .title(createBannerRequest.title())
                .content(createBannerRequest.content())
                .linkUrl(createBannerRequest.linkUrl())
                .imageFileName(imageFileName)
                .priority(createBannerRequest.priority())
                .visible(createBannerRequest.visible())
                .visibleStartAt(createBannerRequest.visibleStartAt())
                .visibleEndAt(createBannerRequest.visibleEndAt())
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindBannerRequest 객체를 기반으로
     * BannerCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 배너 검색 조건을 구성할 때 사용됩니다.
     *
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
     * @param banner 변환할 Banner 엔티티 (null 가능)
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
                        b.getImageFileName(),
                        b.getPriority(),
                        b.getVisible(),
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