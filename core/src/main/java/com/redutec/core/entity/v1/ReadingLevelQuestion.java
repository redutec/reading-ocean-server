package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.sql.Types;
import java.time.LocalDateTime;

/**
 * ReadingLevelQuestion 엔티티
 * 테이블 정보:
 * - 테이블 명: reading_level_question
 * - 테이블 코멘트: 독서 레벨 진단 항목
 */
@Entity
@Table(name = "reading_level_question")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ReadingLevelQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_level_question_no", nullable = false, updatable = false)
    private Integer readingLevelQuestionNo;

    @Column(name = "reading_level_no", nullable = false)
    private Integer readingLevelNo;

    @Column(name = "required", nullable = false)
    private Boolean required;

    @Column(name = "category", nullable = false, length = 20)
    private String category;

    @Column(name = "sub_category", nullable = false, length = 20)
    private String subCategory;

    @Lob
    @Column(name = "question", nullable = false, columnDefinition = "text")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String question;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "text")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String content;

    @Column(name = "answer", nullable = false)
    private Integer answer;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_id", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}