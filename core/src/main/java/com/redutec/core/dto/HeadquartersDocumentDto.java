 package com.redutec.core.dto;

 import com.redutec.core.meta.LearningMaterialAuthor;
 import com.redutec.core.meta.LearningMaterialCategory;
 import io.swagger.v3.oas.annotations.media.Schema;
 import jakarta.persistence.ElementCollection;
 import jakarta.persistence.EnumType;
 import jakarta.persistence.Enumerated;
 import jakarta.validation.constraints.NotNull;
 import jakarta.validation.constraints.Positive;
 import jakarta.validation.constraints.PositiveOrZero;
 import jakarta.validation.constraints.Size;
 import org.springframework.web.multipart.MultipartFile;

 import java.time.LocalDateTime;
 import java.util.List;

public class HeadquartersDocumentDto {
    @Schema(description = "본사 자료실 등록 요청 객체")
    public record CreateHeadquartersDocumentRequest(
            @Schema(description = "문서 분류(독서목록/교육자료/홍보자료/상담자료/기타)", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            LearningMaterialCategory category,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 100)
            String title,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            String content,

            @Schema(description = "작성자", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            LearningMaterialAuthor author,

            @Schema(description = "첨부파일 목록", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<MultipartFile> attachmentFiles,

            @Schema(description = "활성화 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @NotNull
            Boolean available
    ) {}

    @Schema(description = "본사 자료실 조회 요청 객체")
    public record FindHeadquartersDocumentRequest(
            @Schema(description = "본사 자료실 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> headquartersDocumentIds,

            @Schema(description = "문서 분류(독서목록/교육자료/홍보자료/상담자료/기타)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = LearningMaterialCategory.class)
            @Enumerated(EnumType.STRING)
            List<LearningMaterialCategory> categories,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String title,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String content,

            @Schema(description = "작성자", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = LearningMaterialAuthor.class)
            @Enumerated(EnumType.STRING)
            List<LearningMaterialAuthor> authors,

            @Schema(description = "활성화 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean available,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "본사 자료실 수정 요청 객체")
    public record UpdateHeadquartersDocumentRequest(
            @Schema(description = "문서 분류(독서목록/교육자료/홍보자료/상담자료/기타)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            LearningMaterialCategory category,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String title,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String content,

            @Schema(description = "작성자", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            LearningMaterialAuthor author,

            @Schema(description = "첨부파일 목록", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<MultipartFile> attachmentFiles,

            @Schema(description = "활성화 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "true")
            Boolean available
    ) {}

    @Schema(description = "본사 자료실 응답 객체")
    public record HeadquartersDocumentResponse(
            Long headquartersDocumentId,
            LearningMaterialCategory category,
            String title,
            String content,
            LearningMaterialAuthor author,
            List<String> attachementFiles,
            Boolean available,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "본사 자료실 응답 페이징 객체")
    public record HeadquartersDocumentPageResponse(
            List<HeadquartersDocumentResponse> headquartersDocuments,
            Long totalElements,
            Integer totalPages
    ) {}
}