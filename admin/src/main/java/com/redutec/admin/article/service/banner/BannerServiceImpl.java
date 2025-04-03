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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final ArticleService articleService;
    private final FileUtil fileUtil;
    private final JwtUtil jwtUtil;

    // 업로드 가능한 최대 이미지 파일 크기
    private final long MAX_FILE_SIZE = 10 * 1024 * 1024;
    // 이미지 파일 허용 확장자
    private final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png", "gif"};

    @Override
    @Transactional
    public void create(
            ArticleDto.CreateBannerRequest createBannerRequest,
            MultipartFile pcBannerImageFile,
            MultipartFile mobileBannerImageFile
    ) {
        // 현재 로그인한 어드민의 아이디 조회
        String adminId = Optional.ofNullable(jwtUtil.extractUserIdFromToken((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .orElse("admin");
        log.info("adminId: {}", adminId);
        // pc 배너 이미지 파일 검증
        Optional.ofNullable(pcBannerImageFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> fileUtil.validateFile(file, MAX_FILE_SIZE, ALLOWED_EXTENSIONS));
        // mobile 배너 이미지 파일 검증
        Optional.ofNullable(mobileBannerImageFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> fileUtil.validateFile(file, MAX_FILE_SIZE, ALLOWED_EXTENSIONS));
        // DTO → 엔티티 변환 (각각 BdtArticle과 BdtArticleDisplay)
        BdtArticle article = createBannerRequest.toBdtArticleEntity(adminId);
        BdtArticleDisplay articleDisplay = createBannerRequest.toBdtArticleDisplayEntity(article);
        log.info("article: {}", article.getAdminId());
        log.info("articleDisplay: {}", articleDisplay.getBannerType());
        // 게시물(배너) 및 노출정보 INSERT
        articleService.saveArticleAndDisplay(article, articleDisplay);
        // pcBannerImageFile 업로드 및 첨부파일 엔티티 변환 후 INSERT
        Optional.ofNullable(pcBannerImageFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> {
                    FileUploadResult pcFileUploadResult = fileUtil.uploadFile(file, "/banner/pc");
                    BdtArticleAttachFile pcArticleAttachFile = createBannerRequest.toBdtArticleAttachFileEntity(
                            article,
                            AttachFileValue.AFV002,
                            file.getOriginalFilename(),
                            pcFileUploadResult.getFileUrl()
                    );
                    articleService.saveArticleAttachFile(pcArticleAttachFile);
                });
        // mobileBannerImageFile 업로드 및 첨부파일 엔티티 변환 후 INSERT
        Optional.ofNullable(mobileBannerImageFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> {
                    FileUploadResult mobileFileUploadResult = fileUtil.uploadFile(file, "/banner/mobile");
                    BdtArticleAttachFile mobileArticleAttachFile = createBannerRequest.toBdtArticleAttachFileEntity(
                            article,
                            AttachFileValue.AFV001,
                            file.getOriginalFilename(),
                            mobileFileUploadResult.getFileUrl()
                    );
                    articleService.saveArticleAttachFile(mobileArticleAttachFile);
                });
    }

    /**
     * 조건에 맞는 배너 목록 조회
     * @param findBannerRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 배너 목록 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
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
                            Optional.ofNullable(
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
                            Optional.ofNullable(
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
    @Transactional(readOnly = true)
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

    /**
     * 특정 배너 수정
     * @param bannerNo 수정할 배너 고유번호
     * @param updateBannerRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Integer bannerNo,
            ArticleDto.UpdateBannerRequest updateBannerRequest,
            MultipartFile pcBannerImageFile,
            MultipartFile mobileBannerImageFile
    ) {
        // 현재 로그인한 어드민의 아이디 조회
        String adminId = Optional.ofNullable(jwtUtil.extractUserIdFromToken((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .orElse("admin");
        // 수정할 배너가 존재하는지 확인
        BdtArticle article = articleService.getBdtArticle(bannerNo);
        BdtArticleDisplay articleDisplay = articleService.findBdtArticleDisplay(bannerNo);
        BdtArticleAttachFile pcArticleAttachFile = articleService.findBdtArticleAttachFileByArticleAndAttachFileValue(article, AttachFileValue.AFV002);
        BdtArticleAttachFile mobileArticleAttachFile = articleService.findBdtArticleAttachFileByArticleAndAttachFileValue(article, AttachFileValue.AFV001);
        // PC용 배너 이미지 파일 검증
        Optional.ofNullable(pcBannerImageFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> fileUtil.validateFile(file, MAX_FILE_SIZE, ALLOWED_EXTENSIONS));
        // 모바일용 배너 이미지 파일 검증
        Optional.ofNullable(mobileBannerImageFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> fileUtil.validateFile(file, MAX_FILE_SIZE, ALLOWED_EXTENSIONS));
        // 수정할 정보를 각 엔티티에 더티 체킹 방식으로 세팅 (각각 BdtArticle, BdtArticleDisplay)
        article.updateBdtArticle(
                updateBannerRequest.getCategoryValue(),
                updateBannerRequest.getArticleTitle(),
                updateBannerRequest.getArticleContent(),
                updateBannerRequest.getArticleContentDetail(),
                updateBannerRequest.getDisplayYn(),
                updateBannerRequest.getDomain(),
                adminId
        );
        articleDisplay.updateBdtArticleDisplay(
                updateBannerRequest.getDisplayBeginDatetime(),
                updateBannerRequest.getDisplayEndDatetime(),
                updateBannerRequest.getDisplayNewWindowYn(),
                updateBannerRequest.getLinkUrl(),
                updateBannerRequest.getDisplayOrder(),
                updateBannerRequest.getTextColor(),
                updateBannerRequest.getBackgroundColor(),
                updateBannerRequest.getBannerType(),
                "Y",
                adminId
        );
        // 게시물(배너) 및 노출정보 UPDATE

        // PC&모바일용 배너 이미지 업로드 및 수정할 파일 정보를 BdtArticleAttachFile 엔티티에 더티 체킹 방식으로 세팅 후 UPDATE
    }

    @Override
    public void delete(Integer bannerNo) {
        // 받아온 삭제할 배너 고유번호에 해당하는 엔티티 조회 후 해당 엔티티를 articleService.delete로 DELETE
    }
}
