package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * MissionHistory 엔티티
 * 테이블 정보:
 * - 테이블 명: mission_history
 * - 테이블 코멘트: 미션 히스토리
 */
@Entity
@Table(name = "mission_history", indexes = {
        @Index(name = "idx_mission_history", columnList = "academy_no"),
        @Index(name = "idx_mission_history_account", columnList = "account_no"),
        @Index(name = "idx_mission_history_book_and_account", columnList = "book_no, account_no"),
        @Index(name = "idx_mission_history_book_no", columnList = "book_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MissionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_history_no", nullable = false, updatable = false)
    private Integer missionHistoryNo;

    @Column(name = "book_no", nullable = false)
    private Integer bookNo;

    @Column(name = "apply_count", nullable = false)
    private Integer applyCount;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "academy_no")
    private Integer academyNo;

    @Column(name = "puzzle_answer_count", length = 5)
    private String puzzleAnswerCount;

    @Column(name = "puzzle_answer_rate")
    private Integer puzzleAnswerRate;

    @Column(name = "matching_answer_count", length = 5)
    private String matchingAnswerCount;

    @Column(name = "matching_answer_rate")
    private Integer matchingAnswerRate;

    @Column(name = "ox_answer_count", length = 5)
    private String oxAnswerCount;

    @Column(name = "ox_answer_rate")
    private Integer oxAnswerRate;

    @Column(name = "quiz_answer_count", length = 5)
    private String quizAnswerCount;

    @Column(name = "quiz_answer_rate")
    private Integer quizAnswerRate;

    @Column(name = "quiz_fact_answer_count", length = 5, columnDefinition = "varchar(5) default '0/0'")
    private String quizFactAnswerCount;

    @Column(name = "quiz_inference_answer_count", length = 5, columnDefinition = "varchar(5) default '0/0'")
    private String quizInferenceAnswerCount;

    @Column(name = "quiz_criticism_answer_count", length = 5, columnDefinition = "varchar(5) default '0/0'")
    private String quizCriticismAnswerCount;

    @Column(name = "quiz_appreciation_answer_count", length = 5, columnDefinition = "varchar(5) default '0/0'")
    private String quizAppreciationAnswerCount;

    @Column(name = "quiz_creative_answer_count", length = 5, columnDefinition = "varchar(5) default '0/0'")
    private String quizCreativeAnswerCount;

    @Column(name = "point", columnDefinition = "int default 0")
    private Integer point;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}