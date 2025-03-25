package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * MissionQuizHistory 엔티티
 * 테이블 정보:
 * - 테이블 명: mission_quiz_history
 * - 테이블 코멘트: 독서퀴즈 히스토리 No
 */
@Entity
@Table(name = "mission_quiz_history", indexes = {
        @Index(name = "idx_mission_quiz_history", columnList = "mission_history_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MissionQuizHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_history_no", nullable = false, updatable = false)
    private Integer quizHistoryNo;

    @Column(name = "mission_history_no", nullable = false)
    private Integer missionHistoryNo;

    @Column(name = "mission_quiz_no", nullable = false)
    private Integer missionQuizNo;

    @Column(name = "correct", nullable = false, columnDefinition = "tinyint(1)")
    private Boolean correct;

    @Column(name = "student_answer", length = 200, nullable = false)
    private String studentAnswer;

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