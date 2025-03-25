package com.redutec.core.entity;

import com.redutec.core.entity.key.RatReadingMissionQuizResultKey;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * RatReadingMissionQuizResult 엔티티
 * 테이블 정보:
 * - 테이블 명: rat_reading_mission_quiz_result
 * - 테이블 코멘트: 독서미션 - 독서퀴즈 결과(최초 응시시 INSERT, 재응시시 UPDATE )
 */
@Entity
@Table(name = "rat_reading_mission_quiz_result")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class RatReadingMissionQuizResult {
    @EmbeddedId
    private RatReadingMissionQuizResultKey id;

    @Column(name = "mission_answer_rate", nullable = false, columnDefinition = "DECIMAL(5,2) DEFAULT 1.00")
    private BigDecimal missionAnswerRate;

    @Column(name = "mission_answer_count", nullable = false, length = 5)
    private String missionAnswerCount;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}