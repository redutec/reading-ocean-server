package com.redutec.admin.article.service.banner;

import com.redutec.admin.article.dto.ArticleDto;
import com.redutec.admin.article.service.ArticleService;
import com.redutec.admin.config.JwtUtil;
import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.entity.BdtArticle;
import com.redutec.core.entity.BdtArticleAttachFile;
import com.redutec.core.entity.BdtArticleDisplay;
import com.redutec.core.meta.AttachFileValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final ArticleService articleService;
    private final FileUtil fileUtil;
    private final JwtUtil jwtUtil;

    /**
     * 배너 등록
     * @param createBannerRequest 배너 등록 정보를 담은 DTO
     */
    @Override
    public void create(
            ArticleDto.CreateBannerRequest createBannerRequest,
            MultipartFile pcAttachFile,
            MultipartFile mobileAttachFile
    ) {
        // 파일 검증: 최대 10MB, 허용 확장자: jpg, jpeg, png, gif
        long maxFileSize = 10 * 1024 * 1024; // 10MB
        String[] allowedExtensions = {"jpg", "jpeg", "png", "gif"};
        if (pcAttachFile != null && !pcAttachFile.isEmpty()) {
            fileUtil.validateFile(pcAttachFile, maxFileSize, allowedExtensions);
        }
        if (mobileAttachFile != null && !mobileAttachFile.isEmpty()) {
            fileUtil.validateFile(mobileAttachFile, maxFileSize, allowedExtensions);
        }
        // DTO → 엔티티 변환 (각각 BdtArticle와 BdtArticleDisplay)
        BdtArticle article = createBannerRequest.toBdtArticleEntity();
        BdtArticleDisplay articleDisplay = createBannerRequest.toBdtArticleDisplayEntity(article);
        // 게시물(배너) 및 노출정보 저장
        articleService.saveArticleAndDisplay(article, articleDisplay);
        // 첨부파일 업로드 및 첨부파일 엔티티 저장
        if (pcAttachFile != null && !pcAttachFile.isEmpty()) {
            FileUploadResult pcFileUploadResult = fileUtil.uploadFile(pcAttachFile, "/banner/pc");
            BdtArticleAttachFile pcArticleAttachFile = createAttachFileEntity(
                    article,
                    pcFileUploadResult.getFileUrl(),
                    pcAttachFile.getOriginalFilename(),
                    AttachFileValue.AFV002
            );
            articleService.saveArticleAttachFile(pcArticleAttachFile);
        }
        if (mobileAttachFile != null && !mobileAttachFile.isEmpty()) {
            FileUploadResult mobileFileUploadResult = fileUtil.uploadFile(mobileAttachFile, "/banner/mobile");
            BdtArticleAttachFile mobileArticleAttachFile = createAttachFileEntity(
                    article,
                    mobileFileUploadResult.getFileUrl(),
                    mobileAttachFile.getOriginalFilename(),
                    AttachFileValue.AFV001
            );
            articleService.saveArticleAttachFile(mobileArticleAttachFile);
        }
    }

    /**
     * 조건에 맞는 배너 목록 조회
     * @param findBannerRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 배너 목록 응답 객체
     */
    @Override
    public ArticleDto.BannerPageResponse find(ArticleDto.FindArticleRequest findBannerRequest) {
        // 조건에 맞는 배너 조회(페이징)
        Page<BdtArticle> bannerPage = articleService.find(findBannerRequest);
        // 조회한 배너 엔티티들을 응답 객체로 변환 후 리턴
        List<ArticleDto.BannerResponse> bannerList = bannerPage.getContent().stream()
                .map(banner -> {
                    // BdtArticleDisplay 조회
                    BdtArticleDisplay articleDisplay = articleService.findBdtArticleDisplay(banner.getArticleNo());
                    // PC용 첨부파일 조회 및 응답 객체 변환 (파일이 없으면 null)
                    ArticleDto.ArticleAttachFileResponse pcImageFile =
                            java.util.Optional.ofNullable(
                                            articleService.findBdtArticleAttachFileByArticleAndAttachFileValue(banner, AttachFileValue.AFV002)
                                    ).map(pc -> ArticleDto.ArticleAttachFileResponse.builder()
                                            .articleAttachFileNo(pc.getArticleAttachFileNo())
                                            .articleNo(pc.getArticle().getArticleNo())
                                            .attachFileValue(pc.getAttachFileValue())
                                            .attachFileName(pc.getAttachFileName())
                                            .attachmentFilePath(pc.getAttachmentFilePath())
                                            .registerDatetime(pc.getRegisterDatetime())
                                            .modifyDatetime(pc.getModifyDatetime())
                                            .build())
                                    .orElse(null);
                    // 모바일용 첨부파일 조회 및 응답 객체 변환 (파일이 없으면 null)
                    ArticleDto.ArticleAttachFileResponse mobileImageFile =
                            java.util.Optional.ofNullable(
                                            articleService.findBdtArticleAttachFileByArticleAndAttachFileValue(banner, AttachFileValue.AFV001)
                                    ).map(mobile -> ArticleDto.ArticleAttachFileResponse.builder()
                                            .articleAttachFileNo(mobile.getArticleAttachFileNo())
                                            .articleNo(mobile.getArticle().getArticleNo())
                                            .attachFileValue(mobile.getAttachFileValue())
                                            .attachFileName(mobile.getAttachFileName())
                                            .attachmentFilePath(mobile.getAttachmentFilePath())
                                            .registerDatetime(mobile.getRegisterDatetime())
                                            .modifyDatetime(mobile.getModifyDatetime())
                                            .build())
                                    .orElse(null);
                    // BannerResponse 생성 후 반환
                    return ArticleDto.BannerResponse.fromEntity(banner, articleDisplay, pcImageFile, mobileImageFile);
                })
                .collect(Collectors.toList());
        return ArticleDto.BannerPageResponse.builder()
                .bannerList(bannerList)
                .totalElements(bannerPage.getTotalElements())
                .totalPages(bannerPage.getTotalPages())
                .build();
    }

    /**
     * 특정 배너 상세 조회
     * @param bannerNo 배너 고유번호
     * @return 특정 배너 응답 객체
     */
    @Override
    public ArticleDto.BannerResponse findByBannerNo(Integer bannerNo) {
        // 배너 엔티티 조회
        BdtArticle article = articleService.getBdtArticle(bannerNo);
        // 배너 노출 정보 엔티티 조회
        BdtArticleDisplay articleDisplay = articleService.findBdtArticleDisplay(bannerNo);
        // PC 배너 이미지 엔티티 조회 및 응답 객체로 변환 (null인 경우 null 반환)
        ArticleDto.ArticleAttachFileResponse pcImageFile =
                Optional.ofNullable(articleService.findBdtArticleAttachFileByArticleAndAttachFileValue(article, AttachFileValue.AFV002))
                        .map(pc -> ArticleDto.ArticleAttachFileResponse.builder()
                                .articleAttachFileNo(pc.getArticleAttachFileNo())
                                .articleNo(pc.getArticle().getArticleNo())
                                .attachFileValue(pc.getAttachFileValue())
                                .attachFileName(pc.getAttachFileName())
                                .attachmentFilePath(pc.getAttachmentFilePath())
                                .registerDatetime(pc.getRegisterDatetime())
                                .modifyDatetime(pc.getModifyDatetime())
                                .build())
                        .orElse(null);
        // 모바일 배너 이미지 엔티티 조회 및 응답 객체로 변환 (null인 경우 null 반환)
        ArticleDto.ArticleAttachFileResponse mobileImageFile =
                Optional.ofNullable(articleService.findBdtArticleAttachFileByArticleAndAttachFileValue(article, AttachFileValue.AFV001))
                        .map(mobile -> ArticleDto.ArticleAttachFileResponse.builder()
                                .articleAttachFileNo(mobile.getArticleAttachFileNo())
                                .articleNo(mobile.getArticle().getArticleNo())
                                .attachFileValue(mobile.getAttachFileValue())
                                .attachFileName(mobile.getAttachFileName())
                                .attachmentFilePath(mobile.getAttachmentFilePath())
                                .registerDatetime(mobile.getRegisterDatetime())
                                .modifyDatetime(mobile.getModifyDatetime())
                                .build())
                        .orElse(null);
        // 배너 응답 객체 리턴
        return ArticleDto.BannerResponse.fromEntity(
                article,
                articleDisplay,
                pcImageFile,
                mobileImageFile
        );
    }

    @Override
    public void update(Integer bannerNo, ArticleDto.UpdateBannerRequest updateBannerRequest) {
        // 받아온 수정 요청 객체를 엔티티로 변환하여 articleService.update로 UPDATE
    }

    @Override
    public void delete(Integer bannerNo) {
        // 받아온 삭제할 배너 고유번호에 해당하는 엔티티 조회 후 해당 엔티티를 articleService.delete로 DELETE
    }

    /**
     * 첨부파일 엔티티 생성 헬퍼 메서드
     *
     * @param article   게시물 엔티티
     * @param attachmentFilePath   업로드된 파일의 접근 URL
     * @param attachFileName    업로드한 파일의 이름
     * @param attachFileValue 사용하는 기기를 나타내는 첨부파일 값 (예: PC, MOBILE)
     * @return 생성된 BdtArticleAttachFile 엔티티
     */
    private BdtArticleAttachFile createAttachFileEntity(BdtArticle article,
                                                        String attachmentFilePath,
                                                        String attachFileName,
                                                        AttachFileValue attachFileValue
    ) {
        return BdtArticleAttachFile.builder()
                .article(article)
                .attachFileValue(attachFileValue)
                .attachFileName(attachFileName)
                .attachmentFilePath(attachmentFilePath)
                .useYn("Y")
                .adminId(jwtUtil.extractUserIdFromToken((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .build();
    }
}
