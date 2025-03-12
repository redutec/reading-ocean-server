package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * AccountBookbtiAnswer 엔티티
 * 테이블 정보:
 * - 테이블 명: account_bookbti_answer
 * - 테이블 코멘트: 북BTI 진단 문항별 답변
 */
@Entity
@Table(name = "account_bookbti_answer")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class AccountBookbtiAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_bookbti_answer_no", nullable = false, updatable = false)
    private Integer accountBookbtiAnswerNo;

    @Column(name = "account_bookbti_result_no", nullable = false)
    private Integer accountBookbtiResultNo;

    @Column(name = "bookbti_question_no", nullable = false)
    private Integer bookbtiQuestionNo;

    @Column(name = "answer", length = 10, nullable = false)
    private String answer;

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