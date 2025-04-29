package com.redutec.core.entity;

import com.redutec.core.config.StringListConverter;
import com.redutec.core.meta.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Comment("도서")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("도서 고유번호")
    private Long id;

    @Comment("ISBN")
    @Pattern(regexp = "^[0-9]{13}$", message = "ISBN은 13자리의 숫자로만 구성되어야 합니다.")
    @Column(nullable = false, unique = true, length = 13)
    private String isbn;

    @Comment("제목")
    @Column(nullable = false, length = 100)
    private String title;

    @Comment("지은이")
    @Column(length = 30)
    private String author;

    @Comment("출판사")
    @Column(length = 30)
    private String publisher;

    @Comment("옮긴이")
    @Column(length = 30)
    private String translator;

    @Comment("그림작가")
    @Column(length = 30)
    private String illustrator;

    @Comment("출판일자")
    @Column
    private LocalDate publicationDate;

    @Comment("커버 이미지 파일명")
    @Column
    private String coverImageFileName;

    @Comment("추천 도서 여부")
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean recommended = false;

    @Comment("전자책 제공 여부")
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean ebookAvailable = false;

    @Comment("오디오북 제공 여부")
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean audiobookAvailable = false;

    @Comment("노출 여부")
    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean visible = true;

    @Comment("사용 여부")
    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean enabled = true;

    @Comment("전체 페이지 수")
    @Column
    @PositiveOrZero
    private Integer pageCount;

    @Comment("학년")
    @Column
    @Enumerated(EnumType.STRING)
    private SchoolGrade schoolGrade;

    @Comment("장르")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookGenre genre;

    @Comment("세부 장르")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookSubGenre subGenre;

    @Comment("도서 포인트")
    @Column(nullable = false)
    @PositiveOrZero
    private Integer bookPoints;

    @Comment("RAQ")
    @Column(nullable = false)
    @PositiveOrZero
    private Integer raq;

    @Comment("독서 레벨")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReadingLevel readingLevel;

    @Comment("도서 MBTI")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookMbti bookMbti;

    @Comment("주제")
    @Column(nullable = false, length = 200)
    private String subject;

    @Comment("내용")
    @Column(nullable = false)
    private String content;

    @Comment("수상 및 후보 내역")
    @Column(length = 200)
    private String awardHistory;

    @Comment("수록된 교재명")
    @Column(length = 200)
    private String includedBookName;

    @Comment("기관별 추천 내역")
    @Column(length = 200)
    private String institutionRecommendations;

    @Comment("교육청 추천 내역")
    @Column(length = 200)
    private String educationOfficeRecommendations;

    @Comment("주제어(태그) 목록")
    @Convert(converter = StringListConverter.class)
    @Column(length = 1000)
    private List<String> tags;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void updateBook(
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
            Boolean audiobookAvailable,
            Boolean visible,
            Boolean enabled,
            Integer pageCount,
            SchoolGrade schoolGrade,
            BookGenre genre,
            BookSubGenre subGenre,
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
            List<String> tags
    ) {
        this.isbn = isbn != null ? isbn : this.isbn;
        this.title = title != null ? title : this.title;
        this.author = author != null ? author : this.author;
        this.publisher = publisher != null ? publisher : this.publisher;
        this.translator = translator != null ? translator : this.translator;
        this.illustrator = illustrator != null ? illustrator : this.illustrator;
        this.publicationDate = publicationDate != null ? publicationDate : this.publicationDate;
        this.coverImageFileName = coverImageFileName != null ? coverImageFileName : this.coverImageFileName;
        this.recommended = recommended != null ? recommended : this.recommended;
        this.ebookAvailable = ebookAvailable != null ? ebookAvailable : this.ebookAvailable;
        this.audiobookAvailable = audiobookAvailable != null ? audiobookAvailable : this.audiobookAvailable;
        this.visible = visible != null ? visible : this.visible;
        this.enabled = enabled != null ? enabled : this.enabled;
        this.pageCount = pageCount != null ? pageCount : this.pageCount;
        this.schoolGrade = schoolGrade != null ? schoolGrade : this.schoolGrade;
        this.genre = genre != null ? genre : this.genre;
        this.subGenre = subGenre != null ? subGenre : this.subGenre;
        this.bookPoints = bookPoints != null ? bookPoints : this.bookPoints;
        this.raq = raq != null ? raq : this.raq;
        this.readingLevel = readingLevel != null ? readingLevel : this.readingLevel;
        this.bookMbti = bookMbti != null ? bookMbti : this.bookMbti;
        this.subject = subject != null ? subject : this.subject;
        this.content = content != null ? content : this.content;
        this.awardHistory = awardHistory != null ? awardHistory : this.awardHistory;
        this.includedBookName = includedBookName != null ? includedBookName : this.includedBookName;
        this.institutionRecommendations = institutionRecommendations != null ? institutionRecommendations : this.institutionRecommendations;
        this.educationOfficeRecommendations = educationOfficeRecommendations != null ? educationOfficeRecommendations : this.educationOfficeRecommendations;
        this.tags = tags != null ? tags : this.tags;
    }
}