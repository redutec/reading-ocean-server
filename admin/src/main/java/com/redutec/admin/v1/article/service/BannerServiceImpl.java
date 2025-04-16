package com.redutec.admin.v1.article.service;

import com.redutec.admin.v1.article.dto.BannerDto;
import com.redutec.admin.v1.article.mapper.BannerMapper;
import com.redutec.admin.config.JwtUtil;
import com.redutec.core.config.FileUploadResult;
import com.redutec.core.config.FileUtil;
import com.redutec.core.entity.v1.BdtArticle;
import com.redutec.core.entity.v1.BdtArticleAttachFile;
import com.redutec.core.entity.v1.BdtArticleDisplay;
import com.redutec.core.meta.AttachFileValue;
import com.redutec.core.repository.v1.BdtArticleAttachFileRepository;
import com.redutec.core.repository.v1.BdtArticleDisplayRepository;
import com.redutec.core.repository.v1.BdtArticleRepository;
import com.redutec.core.specification.BdtArticleSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final BannerMapper bannerMapper;
    private final ArticleService articleService;
    private final BdtArticleRepository bdtArticleRepository;
    private final BdtArticleDisplayRepository bdtArticleDisplayRepository;
    private final BdtArticleAttachFileRepository bdtArticleAttachFileRepository;
    private final FileUtil fileUtil;
    private final JwtUtil jwtUtil;

    // 업로드 가능한 최대 이미지 파일 크기
    private final long MAX_FILE_SIZE = 10 * 1024 * 1024;
    // 이미지 파일 허용 확장자
    private final String[] ALLOWED_EXTENSIONS = {"jpg", "jpeg", "png", "gif"};

    @Override
    @Transactional
    public void create(
            BannerDto.CreateBannerRequest createBannerRequest,
            MultipartFile pcBannerImageFile,
            MultipartFile mobileBannerImageFile
    ) {
        // 현재 로그인한 어드민의 아이디 조회
        String adminId = Optional.ofNullable(jwtUtil.extractUserIdFromToken((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .orElse("admin");
        // pc 배너 이미지 파일 검증
        Optional.ofNullable(pcBannerImageFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> fileUtil.validateFile(file, MAX_FILE_SIZE, ALLOWED_EXTENSIONS));
        // mobile 배너 이미지 파일 검증
        Optional.ofNullable(mobileBannerImageFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> fileUtil.validateFile(file, MAX_FILE_SIZE, ALLOWED_EXTENSIONS));
        // DTO → 엔티티 변환 (각각 BdtArticle과 BdtArticleDisplay)
        BdtArticle banner = bannerMapper.toBdtArticleEntity(createBannerRequest);
        BdtArticleDisplay bannerDisplay = bannerMapper.toBdtArticleDisplayEntity(createBannerRequest, banner);
        // 게시물(배너) 및 노출정보 INSERT
        bdtArticleRepository.save(banner);
        bdtArticleDisplayRepository.save(bannerDisplay);
        // pcBannerImageFile 업로드 및 첨부파일 엔티티 변환 후 INSERT
        Optional.ofNullable(pcBannerImageFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> {
                    FileUploadResult pcFileUploadResult = fileUtil.uploadFile(file, "/banner/pc");
                    BdtArticleAttachFile pcBannerAttachFile = bannerMapper.toBdtArticleAttachFileEntity(
                            banner,
                            AttachFileValue.PC,
                            file.getOriginalFilename(),
                            pcFileUploadResult.getFileUrl()
                    );
                    bdtArticleAttachFileRepository.save(pcBannerAttachFile);
                });
        // mobileBannerImageFile 업로드 및 첨부파일 엔티티 변환 후 INSERT
        Optional.ofNullable(mobileBannerImageFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> {
                    FileUploadResult mobileFileUploadResult = fileUtil.uploadFile(file, "/banner/mobile");
                    BdtArticleAttachFile mobileBannerAttachFile = bannerMapper.toBdtArticleAttachFileEntity(
                            banner,
                            AttachFileValue.MOBILE,
                            file.getOriginalFilename(),
                            mobileFileUploadResult.getFileUrl()
                    );
                    bdtArticleAttachFileRepository.save(mobileBannerAttachFile);
                });
    }

    /**
     * 조건에 맞는 배너 목록 조회
     * @param findBannerRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 배너 목록 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public BannerDto.BannerPageResponse find(BannerDto.FindBannerRequest findBannerRequest) {
        return bannerMapper.toPageResponseDto(bdtArticleRepository.findAll(
                BdtArticleSpecification.findWith(bannerMapper.toCriteria(findBannerRequest)),
                (findBannerRequest.page() != null && findBannerRequest.size() != null)
                        ? PageRequest.of(findBannerRequest.page(), findBannerRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 배너 상세 조회
     * @param bannerNo 배너 고유번호
     * @return 특정 배너 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public BannerDto.BannerResponse findByBannerNo(
            Integer bannerNo
    ) {
        // 배너 엔티티 조회
        BdtArticle banner = articleService.getBdtArticleByArticleNo(bannerNo);
        // 배너 노출 정보 엔티티 조회
        BdtArticleDisplay bannerDisplay = articleService.findBdtArticleDisplayByArticle(banner);
        // PC 배너 이미지 엔티티 조회 및 응답 객체로 변환 (null인 경우 null 반환)
        BannerDto.BannerAttachFileResponse pcImageFile =
                Optional.ofNullable(articleService.findBdtArticleAttachFileByArticleAndAttachFileValue(banner, AttachFileValue.PC))
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
        // 모바일 배너 이미지 엔티티 조회 및 응답 객체로 변환 (null인 경우 null 반환)
        BannerDto.BannerAttachFileResponse mobileImageFile =
                Optional.ofNullable(articleService.findBdtArticleAttachFileByArticleAndAttachFileValue(banner, AttachFileValue.MOBILE))
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
        // 배너 응답 객체 리턴
        return bannerMapper.toResponseDto(banner, bannerDisplay, pcImageFile, mobileImageFile);
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
            BannerDto.UpdateBannerRequest updateBannerRequest,
            MultipartFile pcBannerImageFile,
            MultipartFile mobileBannerImageFile
    ) {
        // 현재 로그인한 어드민의 아이디 조회
        String adminId = Optional.ofNullable(jwtUtil.extractUserIdFromToken((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .orElse("admin");
        // 수정할 배너가 존재하는지 확인
        BdtArticle banner = articleService.getBdtArticleByArticleNo(bannerNo);
        BdtArticleDisplay bannerDisplay = articleService.findBdtArticleDisplayByArticle(banner);
        BdtArticleAttachFile pcBannerAttachFile = articleService.findBdtArticleAttachFileByArticleAndAttachFileValue(banner, AttachFileValue.PC);
        BdtArticleAttachFile mobileBannerAttachFile = articleService.findBdtArticleAttachFileByArticleAndAttachFileValue(banner, AttachFileValue.MOBILE);
        // PC용 배너 이미지 파일 검증
        Optional.ofNullable(pcBannerImageFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> fileUtil.validateFile(file, MAX_FILE_SIZE, ALLOWED_EXTENSIONS));
        // 모바일용 배너 이미지 파일 검증
        Optional.ofNullable(mobileBannerImageFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> fileUtil.validateFile(file, MAX_FILE_SIZE, ALLOWED_EXTENSIONS));
        // 수정할 정보를 각 엔티티에 더티 체킹 방식으로 세팅 (각각 BdtArticle, BdtArticleDisplay)
        banner.updateBdtArticle(
                null,
                null,
                updateBannerRequest.bannerTitle(),
                updateBannerRequest.bannerContent(),
                updateBannerRequest.bannerContentDetail(),
                updateBannerRequest.displayYn(),
                updateBannerRequest.domain(),
                adminId
        );
        bannerDisplay.updateBdtArticleDisplay(
                updateBannerRequest.displayBeginDatetime(),
                updateBannerRequest.displayEndDatetime(),
                updateBannerRequest.displayNewWindowYn(),
                updateBannerRequest.linkUrl(),
                updateBannerRequest.displayOrder(),
                updateBannerRequest.textColor(),
                updateBannerRequest.backgroundColor(),
                updateBannerRequest.bannerType(),
                updateBannerRequest.displayYn(),
                adminId
        );
        // 게시물(배너) 및 노출정보 UPDATE
        bdtArticleRepository.save(banner);
        bdtArticleDisplayRepository.save(bannerDisplay);
        // pcBannerImageFile 업로드 및 첨부파일 엔티티 변환 후 UPDATE
        Optional.ofNullable(pcBannerImageFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> {
                    FileUploadResult pcFileUploadResult = fileUtil.uploadFile(file, "/banner/pc");
                    BdtArticleAttachFile pcAttachFile = bannerMapper.toBdtArticleAttachFileEntity(
                            banner,
                            AttachFileValue.PC,
                            file.getOriginalFilename(),
                            pcFileUploadResult.getFileUrl()
                    );
                    bdtArticleAttachFileRepository.save(pcAttachFile);
                });
        // mobileBannerImageFile 업로드 및 첨부파일 엔티티 변환 후 UPDATE
        Optional.ofNullable(mobileBannerImageFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> {
                    FileUploadResult mobileFileUploadResult = fileUtil.uploadFile(file, "/banner/mobile");
                    BdtArticleAttachFile mobileAttachFile = bannerMapper.toBdtArticleAttachFileEntity(
                            banner,
                            AttachFileValue.MOBILE,
                            file.getOriginalFilename(),
                            mobileFileUploadResult.getFileUrl()
                    );
                    bdtArticleAttachFileRepository.save(mobileAttachFile);
                });
    }
}
