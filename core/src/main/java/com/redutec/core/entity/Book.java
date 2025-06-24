package com.redutec.core.entity;

import com.redutec.core.config.StringListConverter;
import com.redutec.core.meta.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
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
    @Setter
    @Pattern(regexp = "^[0-9]{13}$", message = "ISBN은 13자리의 숫자로만 구성되어야 합니다.")
    @Column(nullable = false, unique = true, length = 13)
    private String isbn;

    @Comment("제목")
    @Setter
    @Column(nullable = false, length = 100)
    private String title;

    @Comment("지은이")
    @Setter
    @Column(length = 30)
    private String author;

    @Comment("출판사")
    @Setter
    @Column(length = 30)
    private String publisher;

    @Comment("옮긴이")
    @Setter
    @Column(length = 30)
    private String translator;

    @Comment("그림작가")
    @Setter
    @Column(length = 30)
    private String illustrator;

    @Comment("출판일자")
    @Setter
    @Column
    private LocalDate publicationDate;

    @Comment("커버 이미지 파일명")
    @Setter
    @Column
    private String coverImageFileName;

    @Comment("추천 도서 여부")
    @Setter
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean recommended = false;

    @Comment("전자책 제공 여부")
    @Setter
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean ebookAvailable = false;

    @Comment("오디오북 제공 여부")
    @Setter
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean audioBookAvailable = false;

    @Comment("노출 여부")
    @Setter
    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean visible = true;

    @Comment("사용 여부")
    @Setter
    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean enabled = true;

    @Comment("전체 페이지 수")
    @Setter
    @Column
    @PositiveOrZero
    private Integer pageCount;

    @Comment("학년")
    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    private SchoolGrade schoolGrade;

    @Comment("장르")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookGenre genre;

    @Comment("세부 장르")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookSubGenre subGenre;

    @Comment("도서 포인트")
    @Setter
    @Column(nullable = false)
    @PositiveOrZero
    private Integer bookPoints;

    @Comment("RAQ")
    @Setter
    @Column(nullable = false)
    @PositiveOrZero
    private Integer raq;

    @Comment("독서 레벨")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReadingLevel readingLevel;

    @Comment("도서 MBTI")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookMbti bookMbti;

    @Comment("주제")
    @Setter
    @Column(nullable = false, length = 200)
    private String subject;

    @Comment("내용")
    @Setter
    @Column(nullable = false)
    private String content;

    @Comment("수상 및 후보 내역")
    @Setter
    @Column(length = 200)
    private String awardHistory;

    @Comment("수록된 교재명")
    @Setter
    @Column(length = 200)
    private String includedBookName;

    @Comment("기관별 추천 내역")
    @Setter
    @Column(length = 200)
    private String institutionRecommendations;

    @Comment("교육청 추천 내역")
    @Setter
    @Column(length = 200)
    private String educationOfficeRecommendations;

    @Comment("주제어(태그) 목록")
    @Setter
    @Convert(converter = StringListConverter.class)
    @Column(length = 1000)
    private List<String> tags;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}