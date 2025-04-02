package com.redutec.admin.article.service.banner;

import com.redutec.admin.article.dto.ArticleDto;
import com.redutec.admin.article.service.ArticleService;
import com.redutec.core.entity.BdtArticle;
import com.redutec.core.entity.BdtArticleAttachFile;
import com.redutec.core.entity.BdtArticleDisplay;
import com.redutec.core.meta.AttachFileValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {
    private final ArticleService articleService;

    @Override
    public void create(
            ArticleDto.CreateArticleRequest createArticleRequest,
            MultipartFile pcAttachFile,
            MultipartFile mobileAttachFile
    ) {
        // 업로드한 파일의 용량과 확장자 검증 후 업로드 처리(파일이 존재하는 경우에만 저장)
        // 받아온 등록 요청 객체를 각각의 엔티티(BdtArticle, BdtArticleDisplay, BdtArticleAttachFile(파일이 존재하는 경우에만))로 변환
        // 게시물 저장 서비스를 호출하여 엔티티 저장
    }

    @Override
    public ArticleDto.BannerPageResponse find(ArticleDto.FindArticleRequest findBannerRequest) {
        // 조건에 맞는 배너 조회(페이징)
        Page<BdtArticle> bannerPage = articleService.find(findBannerRequest);
        // 조회한 배너 엔티티들을 응답 객체로 변환 후 리턴
        List<ArticleDto.BannerResponse> bannerList = bannerPage.getContent().stream()
                .map(banner -> {
                    // BdtArticleDisplay 조회
                    BdtArticleDisplay articleDisplay = articleService.getBdtArticleDisplay(banner.getArticleNo());
                    // PC용 첨부파일 조회
                    BdtArticleAttachFile pcAttachFile = articleService.getBdtArticleAttachFileByArticleAndAttachFileValue(
                            banner,
                            AttachFileValue.PC
                    );
                    // 모바일용 첨부파일 조회
                    BdtArticleAttachFile mobileAttachFile = articleService.getBdtArticleAttachFileByArticleAndAttachFileValue(
                            banner,
                            AttachFileValue.MOBILE
                    );
                    // BdtArticleAttachFile을 ArticleAttachFileResponse로 변환
                    ArticleDto.ArticleAttachFileResponse pcImageFile = (pcAttachFile != null)
                            ? ArticleDto.ArticleAttachFileResponse.builder()
                            .articleAttachFileNo(pcAttachFile.getArticleAttachFileNo())
                            .articleNo(pcAttachFile.getArticle().getArticleNo())
                            .attachFileValue(pcAttachFile.getAttachFileValue())
                            .attachFileName(pcAttachFile.getAttachFileName())
                            .attachmentFilePath(pcAttachFile.getAttachmentFilePath())
                            .registerDatetime(pcAttachFile.getRegisterDatetime())
                            .modifyDatetime(pcAttachFile.getModifyDatetime())
                            .build()
                            : null;
                    ArticleDto.ArticleAttachFileResponse mobileImageFile = (mobileAttachFile != null)
                            ? ArticleDto.ArticleAttachFileResponse.builder()
                            .articleAttachFileNo(mobileAttachFile.getArticleAttachFileNo())
                            .articleNo(mobileAttachFile.getArticle().getArticleNo())
                            .attachFileValue(mobileAttachFile.getAttachFileValue())
                            .attachFileName(mobileAttachFile.getAttachFileName())
                            .attachmentFilePath(mobileAttachFile.getAttachmentFilePath())
                            .registerDatetime(mobileAttachFile.getRegisterDatetime())
                            .modifyDatetime(mobileAttachFile.getModifyDatetime())
                            .build()
                            : null;
                    // BannerResponse 생성
                    return ArticleDto.BannerResponse.fromEntity(banner, articleDisplay, pcImageFile, mobileImageFile);
                })
                .collect(Collectors.toList());
        return ArticleDto.BannerPageResponse.builder()
                .bannerList(bannerList)
                .totalElements(bannerPage.getTotalElements())
                .totalPages(bannerPage.getTotalPages())
                .build();
    }

    @Override
    public ArticleDto.BannerResponse findByBannerNo(Integer bannerNo) {
        // 배너 엔티티 조회
        BdtArticle article = articleService.getBdtArticle(bannerNo);
        // 배너 노출 정보 엔티티 조회
        BdtArticleDisplay articleDisplay = articleService.getBdtArticleDisplay(bannerNo);
        // PC 배너 이미지 엔티티 조회 및 응답 객체로 변환
        BdtArticleAttachFile pcArticleAttachFile = articleService.getBdtArticleAttachFileByArticleAndAttachFileValue(
                article,
                AttachFileValue.PC
        );
        ArticleDto.ArticleAttachFileResponse pcImageFile = ArticleDto.ArticleAttachFileResponse.builder()
                .articleAttachFileNo(pcArticleAttachFile.getArticleAttachFileNo())
                .articleNo(pcArticleAttachFile.getArticle().getArticleNo())
                .attachFileValue(pcArticleAttachFile.getAttachFileValue())
                .attachFileName(pcArticleAttachFile.getAttachFileName())
                .attachmentFilePath(pcArticleAttachFile.getAttachmentFilePath())
                .registerDatetime(pcArticleAttachFile.getRegisterDatetime())
                .modifyDatetime(pcArticleAttachFile.getModifyDatetime())
                .build();
        // 모바일 배너 이미지 엔티티 조회 및 응답 객체로 변환
        BdtArticleAttachFile mobileArticleAttachFile = articleService.getBdtArticleAttachFileByArticleAndAttachFileValue(
                article,
                AttachFileValue.MOBILE
        );
        ArticleDto.ArticleAttachFileResponse mobileImageFile = ArticleDto.ArticleAttachFileResponse.builder()
                .articleAttachFileNo(mobileArticleAttachFile.getArticleAttachFileNo())
                .articleNo(mobileArticleAttachFile.getArticle().getArticleNo())
                .attachFileValue(mobileArticleAttachFile.getAttachFileValue())
                .attachFileName(mobileArticleAttachFile.getAttachFileName())
                .attachmentFilePath(mobileArticleAttachFile.getAttachmentFilePath())
                .registerDatetime(mobileArticleAttachFile.getRegisterDatetime())
                .modifyDatetime(mobileArticleAttachFile.getModifyDatetime())
                .build();
        // 배너 응답 객체 리턴
        return ArticleDto.BannerResponse.fromEntity(
                article,
                articleDisplay,
                pcImageFile,
                mobileImageFile
        );
    }

    @Override
    public void update(Integer bannerNo, ArticleDto.UpdateArticleRequest updateBannerRequest) {
        // 받아온 수정 요청 객체를 엔티티로 변환하여 articleService.update로 UPDATE
    }

    @Override
    public void delete(Integer bannerNo) {
        // 받아온 삭제할 배너 고유번호에 해당하는 엔티티 조회 후 해당 엔티티를 articleService.delete로 DELETE
    }
}
