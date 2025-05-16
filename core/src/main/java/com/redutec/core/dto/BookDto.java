package com.redutec.core.dto;

import com.redutec.core.meta.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BookDto {
    @Schema(description = "도서 등록 요청 객체")
    public record CreateBookRequest(
            @Schema(description = "ISBN", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Pattern(regexp = "^[0-9]{13}$", message = "ISBN은 13자리의 숫자로만 구성되어야 합니다")
            String isbn,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 100)
            String title,

            @Schema(description = "지은이", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 30)
            String author,

            @Schema(description = "출판사", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 30)
            String publisher,

            @Schema(description = "옮긴이", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 30)
            String translator,

            @Schema(description = "그림작가", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 30)
            String illustrator,

            @Schema(description = "출판일자", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDate publicationDate,

            @Schema(description = "커버 이미지 파일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            MultipartFile coverImageFile,

            @Schema(description = "추천 도서 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
            @NotNull
            Boolean recommended,

            @Schema(description = "전자책 제공 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
            @NotNull
            Boolean ebookAvailable,

            @Schema(description = "오디오북 제공 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
            @NotNull
            Boolean audioBookAvalable,

            @Schema(description = "노출 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @NotNull
            Boolean visible,

            @Schema(description = "사용 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
            @NotNull
            Boolean enabled,

            @Schema(description = "전체 페이지 수", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer pageCount,

            @Schema(description = "학년", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            SchoolGrade schoolGrade,

            @Schema(description = "장르", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            BookGenre genre,

            @Schema(description = "세부 장르", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            BookSubGenre subGenre,

            @Schema(description = "도서 포인트", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
            @NotNull
            @PositiveOrZero
            Integer bookPoints,

            @Schema(description = "RAQ", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @PositiveOrZero
            Integer raq,

            @Schema(description = "독서 레벨", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            ReadingLevel readingLevel,

            @Schema(description = "도서 MBTI 유형", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Enumerated(EnumType.STRING)
            BookMbti bookMbti,

            @Schema(description = "주제", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 200)
            String subject,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            String content,

            @Schema(description = "수상 및 후보 내역", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String awardHistory,

            @Schema(description = "수록된 교재명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String includedBookName,

            @Schema(description = "기관별 추천 내역", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String institutionRecommendations,

            @Schema(description = "교육청 추천 내역", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String educationOfficeRecommendations,

            @Schema(description = "주제어(태그) 목록", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<String> tags
    ) {}

    @Schema(description = "도서 조회 요청 객체")
    public record FindBookRequest(
            @Schema(description = "도서 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> bookIds,

            @Schema(description = "ISBN", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "^[0-9]{1,13}$", message = "검색하실 ISBN은 최대 13자리의 숫자로만 구성되어야 합니다")
            String isbn,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String title,

            @Schema(description = "지은이", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String author,

            @Schema(description = "출판사", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String publisher,

            @Schema(description = "추천 도서 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean recommended,

            @Schema(description = "전자책 제공 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean ebookAvailable,

            @Schema(description = "오디오북 제공 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean audioBookAvailable,

            @Schema(description = "노출 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean visible,

            @Schema(description = "사용 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean enabled,

            @Schema(description = "최소 페이지 수", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Integer minimumPageCount,

            @Schema(description = "최대 페이지 수", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Integer maximumPageCount,

            @Schema(description = "최소 학년", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Integer minimumSchoolGrade,

            @Schema(description = "최대 학년", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Integer maximumSchoolGrade,

            @Schema(description = "장르", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = BookGenre.class)
            @Enumerated(EnumType.STRING)
            List<BookGenre> genres,

            @Schema(description = "세부 장르", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = BookSubGenre.class)
            @Enumerated(EnumType.STRING)
            List<BookSubGenre> subGenres,

            @Schema(description = "최소 도서 포인트", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Integer minimumBookPoints,

            @Schema(description = "최대 도서 포인트", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Integer maximumBookPoints,

            @Schema(description = "최소 RAQ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Integer minimumRaq,

            @Schema(description = "최대 RAQ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Integer maximumRaq,

            @Schema(description = "도서 MBTI", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = BookMbti.class)
            @Enumerated(EnumType.STRING)
            List<BookMbti> bookMbtiList,

            @Schema(description = "주제", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String subject,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String content,

            @Schema(description = "주제어(태그) 목록", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<String> tags,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "도서 수정 요청 객체")
    public record UpdateBookRequest(
            @Schema(description = "ISBN", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Pattern(regexp = "^[0-9]{13}$", message = "ISBN은 13자리의 숫자로만 구성되어야 합니다")
            String isbn,

            @Schema(description = "제목", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 100)
            String title,

            @Schema(description = "지은이", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 30)
            String author,

            @Schema(description = "출판사", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 30)
            String publisher,

            @Schema(description = "옮긴이", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 30)
            String translator,

            @Schema(description = "그림작가", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 30)
            String illustrator,

            @Schema(description = "출판일자", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            LocalDate publicationDate,

            @Schema(description = "커버 이미지 파일", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            MultipartFile coverImageFile,

            @Schema(description = "추천 도서 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean recommended,

            @Schema(description = "전자책 제공 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean ebookAvailable,

            @Schema(description = "오디오북 제공 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean audioBookAvalable,

            @Schema(description = "노출 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean visible,

            @Schema(description = "사용 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean enabled,

            @Schema(description = "전체 페이지 수", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer pageCount,

            @Schema(description = "학년", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            SchoolGrade schoolGrade,

            @Schema(description = "장르", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            BookGenre genre,

            @Schema(description = "세부 장르", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            BookSubGenre subGenre,

            @Schema(description = "도서 포인트", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer bookPoints,

            @Schema(description = "RAQ", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer raq,

            @Schema(description = "독서 레벨", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            ReadingLevel readingLevel,

            @Schema(description = "도서 MBTI 유형", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Enumerated(EnumType.STRING)
            BookMbti bookMbti,

            @Schema(description = "주제", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String subject,

            @Schema(description = "내용", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String content,

            @Schema(description = "수상 및 후보 내역", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String awardHistory,

            @Schema(description = "수록된 교재명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String includedBookName,

            @Schema(description = "기관별 추천 내역", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String institutionRecommendations,

            @Schema(description = "교육청 추천 내역", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 200)
            String educationOfficeRecommendations,

            @Schema(description = "주제어(태그) 목록", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<String> tags
    ) {}

    @Schema(description = "도서 응답 객체")
    public record BookResponse(
            Long bookId,
            String isbn,
            String title,
            String author,
            String publisher,
            String translator,
            String illustrator,
            LocalDate publicationDate,
            String coverImageFileName,
            Boolean recommended,
            Boolean ebookAvailable,
            Boolean audioBookAvalable,
            Boolean visible,
            Boolean enabled,
            Integer pageCount,
            String schoolGrade,
            String genre,
            String subGenre,
            Integer bookPoints,
            Integer raq,
            ReadingLevel readingLevel,
            BookMbti bookMbti,
            String subject,
            String content,
            String awardHistory,
            String includedBookName,
            String institutionRecommendations,
            String educationOfficeRecommendations,
            List<String> tags,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "도서 응답 페이징 객체")
    public record BookPageResponse(
            List<BookResponse> books,
            Long totalElements,
            Integer totalPages
    ) {}
}