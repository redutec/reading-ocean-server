package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * ReadingLevelQuestionExample 엔티티
 * 테이블 정보:
 * - 테이블 명: reading_level_question_example
 * - 테이블 코멘트: 독서 레벨 진단 문항 보기
 */
@Entity
@Table(name = "reading_level_question_example")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ReadingLevelQuestionExample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_level_question_example_no", nullable = false, updatable = false)
    private Integer readingLevelQuestionExampleNo;

    @Column(name = "reading_level_question_no", nullable = false)
    private Integer readingLevelQuestionNo;

    @Column(name = "content", length = 300, nullable = false)
    private String content;

    @Column(name = "value", nullable = false)
    private Integer value;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_id", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}