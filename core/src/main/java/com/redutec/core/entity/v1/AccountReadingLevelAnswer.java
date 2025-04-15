package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * AccountReadingLevelAnswer 엔티티
 * 테이블 정보:
 * - 테이블 명: account_reading_level_answer
 * - 테이블 코멘트: 독서 레벨 진단 문항별 답변
 */
@Entity
@Table(name = "account_reading_level_answer")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class AccountReadingLevelAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_reading_level_answer_no", nullable = false, updatable = false)
    private Integer accountReadingLevelAnswerNo;

    @Column(name = "account_reading_level_result_no", nullable = false)
    private Integer accountReadingLevelResultNo;

    @Column(name = "reading_level_question_no", nullable = false)
    private Integer readingLevelQuestionNo;

    @Column(name = "answer", nullable = false)
    private Integer answer;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_id", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}