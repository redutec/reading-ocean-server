package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * CttBook 엔티티
 * 테이블 정보:
 * - 테이블 명: ctt_book
 * - 테이블 코멘트: 도서
 */
@Entity
@Table(name = "ctt_book", uniqueConstraints = {
        @UniqueConstraint(name = "ctt_book_pk", columnNames = "ISBN")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CttBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_no", nullable = false, updatable = false)
    private Integer bookNo;

    @Column(name = "book_name", length = 100, nullable = false)
    private String bookName;

    @Column(name = "publisher_name", length = 30, nullable = false)
    private String publisherName;

    @Column(name = "author_name", length = 30, nullable = false)
    private String authorName;

    @Column(name = "translator_name", length = 30)
    private String translatorName;

    @Column(name = "ISBN", length = 20)
    private String ISBN;

    @Column(name = "illustrator_name", length = 30)
    private String illustratorName;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "book_image_path", length = 200)
    private String bookImagePath;

    @Column(name = "recommend_book_yn", columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String recommendBookYn;

    @Column(name = "include_ebook_yn", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String includeEbookYn;

    @Column(name = "include_audiobook_yn", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String includeAudiobookYn;

    @Column(name = "total_page_count", columnDefinition = "SMALLINT DEFAULT 0")
    private Short totalPageCount;

    @Column(name = "school_grade", length = 15)
    private String schoolGrade;

    @Column(name = "book_section_type", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String bookSectionType;

    @Column(name = "book_sub_section_type", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String bookSubSectionType;

    @Column(name = "book_point", nullable = false)
    private Short bookPoint;

    @Column(name = "RAQ", nullable = false)
    private Short RAQ;

    @Column(name = "reading_level", length = 10)
    private String readingLevel;

    @Column(name = "personality_type_value", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String personalityTypeValue;

    @Column(name = "book_subject", length = 200, nullable = false)
    private String bookSubject;

    @Lob
    @Column(name = "book_content", nullable = false, columnDefinition = "text")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String bookContent;

    @Column(name = "prize_piece", length = 200)
    private String prizePiece;

    @Column(name = "include_book", length = 200)
    private String includeBook;

    @Column(name = "institution_recommend", length = 200)
    private String institutionRecommend;

    @Column(name = "educationoffice_recommend", length = 200)
    private String educationofficeRecommend;

    @Column(name = "keyword_configuration", length = 200)
    private String keywordConfiguration;

    @Column(name = "display_yn", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String displayYn;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1) default 'Y'")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @Column(name = "register_reading_quiz_yn", nullable = false, columnDefinition = "char(1) default 'N'")
    @JdbcTypeCode(Types.CHAR)
    private String registerReadingQuizYn;

    @Column(name = "register_word_puzzle_yn", nullable = false, columnDefinition = "char(1) default 'N'")
    @JdbcTypeCode(Types.CHAR)
    private String registerWordPuzzleYn;

    @Column(name = "register_OX_quiz_yn", nullable = false, columnDefinition = "char(1) default 'N'")
    @JdbcTypeCode(Types.CHAR)
    private String registerOXQuizYn;

    @Column(name = "register_matching_game_yn", nullable = false, columnDefinition = "char(1) default 'N'")
    @JdbcTypeCode(Types.CHAR)
    private String registerMatchingGameYn;

    @Column(name = "register_opinion_yn", nullable = false, columnDefinition = "char(1) default 'N'")
    @JdbcTypeCode(Types.CHAR)
    private String registerOpinionYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30, nullable = false)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}