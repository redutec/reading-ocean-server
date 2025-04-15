package com.redutec.admin.article.mapper;

import com.redutec.admin.article.dto.BannerDto;
import com.redutec.admin.article.service.ArticleService;
import com.redutec.admin.config.JwtUtil;
import com.redutec.core.criteria.BdtArticleCriteria;
import com.redutec.core.entity.v1.BdtArticle;
import com.redutec.core.entity.v1.BdtArticleAttachFile;
import com.redutec.core.entity.v1.BdtArticleDisplay;
import com.redutec.core.meta.AttachFileValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class BannerMapper {
    private final ArticleService articleService;
    private final JwtUtil jwtUtil;

    /**
     * CreateBannerRequest DTO를 기반으로 BdtArticle 엔티티를 생성합니다.
     *
     * @param createBannerRequest 사이트 설정 생성에 필요한 데이터를 담은 DTO
     * @return BdtArticle 엔티티
     */
    public BdtArticle toBdtArticleEntity(
            BannerDto.CreateBannerRequest createBannerRequest
    ) {
        String adminId = Optional.ofNullable(jwtUtil.extractUserIdFromToken((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .orElse("admin");
        return BdtArticle.builder()
                .categoryValue(null)
                .articleBoardNo(5)
                .articleTitle(createBannerRequest.bannerTitle())
                .articleContent(createBannerRequest.bannerContent())
                .articleContentDetail(createBannerRequest.bannerContentDetail())
                .displayYn(createBannerRequest.displayYn())
                .displayDomainCode("DMC002")
                .domain(createBannerRequest.domain())
                .displayConfigurationYn("Y")
                .adminId(adminId)
                .build();
    }

    public BdtArticleDisplay toBdtArticleDisplayEntity(
            BannerDto.CreateBannerRequest createBannerRequest,
            BdtArticle article
    ) {
        return BdtArticleDisplay.builder()
                .article(article)
                .textColor(createBannerRequest.textColor())
                .backgroundColor(createBannerRequest.backgroundColor())
                .displayOrder(createBannerRequest.displayOrder())
                .bannerType(createBannerRequest.bannerType())
                .linkURL(createBannerRequest.linkUrl())
                .displayNewWindowYn(createBannerRequest.displayNewWindowYn())
                .displayBeginDatetime(createBannerRequest.displayBeginDatetime())
                .displayEndDatetime(createBannerRequest.displayEndDatetime())
                .useYn(article.getUseYn())
                .adminId(article.getAdminId())
                .build();
    }

    public BdtArticleAttachFile toBdtArticleAttachFileEntity(
            BdtArticle article,
            AttachFileValue attachFileValue,
            String attachFileName,
            String attachmentFilePath
    ) {
        return BdtArticleAttachFile.builder()
                .article(article)
                .attachFileValue(attachFileValue)
                .attachFileName(attachFileName)
                .attachmentFilePath(attachmentFilePath)
                .useYn("Y")
                .adminId(article.getAdminId())
                .build();
    }

    /**
     * FindBannerRequest DTO를 기반으로 검색 조건 객체인 BdtArticleCriteria를 생성합니다.
     * 내부 검색 로직에서 사이트 설정 검색 조건을 구성할 때 사용됩니다.
     *
     * @param findBannerRequest 사이트 설정 조회 조건을 담은 DTO
     * @return 해당 요청의 필드를 이용해 생성된 BdtArticleCriteria 객체
     */
    public BdtArticleCriteria toCriteria(
            BannerDto.FindBannerRequest findBannerRequest
    ) {
        return BdtArticleCriteria.builder()
                .articleNoList(findBannerRequest.bannerNoList())
                .articleTitle(findBannerRequest.bannerTitle())
                .articleContent(findBannerRequest.bannerContent())
                .articleContentDetail(findBannerRequest.bannerContentDetail())
                .displayYn(findBannerRequest.displayYn())
                .domainList(findBannerRequest.domainList())
                .build();
    }

    /**
     * Banner 엔티티를 기반으로 응답용 BannerResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param bdtArticle 변환할 엔티티 (null 가능)
     * @return Banner 엔티티의 데이터를 담은 BannerResponse DTO, company가 null이면 null 반환
     */
    public BannerDto.BannerResponse toResponseDto(
            BdtArticle bdtArticle,
            BdtArticleDisplay bdtArticleDisplay,
            BannerDto.BannerAttachFileResponse pcImageFile,
            BannerDto.BannerAttachFileResponse mobileImageFile
    ) {
        return new BannerDto.BannerResponse(
                bdtArticle.getArticleNo(),
                bdtArticle.getArticleTitle(),
                bdtArticle.getArticleContent(),
                bdtArticle.getArticleContentDetail(),
                bdtArticle.getDomain(),
                bdtArticle.getDisplayYn(),
                bdtArticleDisplay != null ? bdtArticleDisplay.getDisplayOrder() : null,
                bdtArticleDisplay != null ? bdtArticleDisplay.getBannerType() : null,
                bdtArticleDisplay != null ? bdtArticleDisplay.getBackgroundColor() : null,
                bdtArticleDisplay != null ? bdtArticleDisplay.getTextColor() : null,
                bdtArticleDisplay != null ? bdtArticleDisplay.getDisplayBeginDatetime() : null,
                bdtArticleDisplay != null ? bdtArticleDisplay.getDisplayEndDatetime() : null,
                bdtArticleDisplay != null ? bdtArticleDisplay.getLinkURL() : null,
                bdtArticleDisplay != null ? bdtArticleDisplay.getDisplayNewWindowYn() : null,
                pcImageFile,
                mobileImageFile,
                bdtArticle.getRegisterDatetime(),
                bdtArticle.getModifyDatetime()
                );
    }

    /**
     * Page 형식의 엔티티 목록을 BannerPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param bdtArticlePage Page 형태로 조회된 엔티티 목록 (null 가능)
     * @return Banner 엔티티 리스트와 페이지 정보를 담은 BannerPageResponse DTO, bdtArticlePage가 null이면 null 반환
     */
    public BannerDto.BannerPageResponse toPageResponseDto(
            Page<BdtArticle> bdtArticlePage
    ) {
        return Optional.ofNullable(bdtArticlePage)
                .map(page -> {
                    List<BannerDto.BannerResponse> bannerList = page.getContent().stream()
                            .map(banner -> {
                                // BdtArticleDisplay 조회
                                BdtArticleDisplay articleDisplay = articleService.findBdtArticleDisplayByArticle(banner);
                                // PC용 첨부파일 조회 및 응답 객체 변환 (파일이 없으면 null)
                                BannerDto.BannerAttachFileResponse pcImageFile = Optional.ofNullable(
                                        articleService.findBdtArticleAttachFileByArticleAndAttachFileValue(
                                                banner,
                                                AttachFileValue.AFV002
                                        ))
                                        .map(pcImage -> new BannerDto.BannerAttachFileResponse(
                                                pcImage.getArticleAttachFileNo(),
                                                pcImage.getArticle().getArticleNo(),
                                                pcImage.getAttachFileValue(),
                                                pcImage.getAttachFileName(),
                                                pcImage.getAttachmentFilePath(),
                                                pcImage.getRegisterDatetime(),
                                                pcImage.getModifyDatetime()
                                        ))
                                        .orElse(null);
                                // 모바일용 첨부파일 조회 및 응답 객체 변환 (파일이 없으면 null)
                                BannerDto.BannerAttachFileResponse mobileImageFile = Optional.ofNullable(
                                        articleService.findBdtArticleAttachFileByArticleAndAttachFileValue(
                                                banner,
                                                AttachFileValue.AFV001
                                        ))
                                        .map(mobileImage -> new BannerDto.BannerAttachFileResponse(
                                                mobileImage.getArticleAttachFileNo(),
                                                mobileImage.getArticle().getArticleNo(),
                                                mobileImage.getAttachFileValue(),
                                                mobileImage.getAttachFileName(),
                                                mobileImage.getAttachmentFilePath(),
                                                mobileImage.getRegisterDatetime(),
                                                mobileImage.getModifyDatetime()
                                        ))
                                        .orElse(null);
                                // toResponseDto 메서드를 사용하여 BannerResponse 객체 생성
                                return toResponseDto(banner, articleDisplay, pcImageFile, mobileImageFile);
                            })
                            .collect(Collectors.toList());
                    return new BannerDto.BannerPageResponse(
                            bannerList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}