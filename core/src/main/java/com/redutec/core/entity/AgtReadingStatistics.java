package com.redutec.core.entity;

import com.redutec.core.entity.key.AgtReadingStatisticsKey;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * AgtReadingStatistics 엔티티
 * 테이블 정보:
 * - 테이블 명: agt_reading_statistics
 * - 테이블 코멘트: 일별 독서 집계(인증도서수, 독서미션별 통과율.. )
 */
@Entity
@Table(name = "agt_reading_statistics")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class AgtReadingStatistics {
    @EmbeddedId
    private AgtReadingStatisticsKey id;

    @Column(name = "month_goal_book_count", nullable = false, columnDefinition = "SMALLINT DEFAULT 0")
    private Short monthGoalBookCount;

    @Column(name = "verification_book_count", nullable = false, columnDefinition = "SMALLINT DEFAULT 0")
    private Short verificationBookCount;

    @Column(name = "quiz_average_answer_rate", nullable = false, columnDefinition = "DECIMAL(5,2) DEFAULT 0.00")
    private BigDecimal quizAverageAnswerRate;

    @Column(name = "crossword_average_answer_rate", nullable = false, columnDefinition = "DECIMAL(5,2) DEFAULT 0.00")
    private BigDecimal crosswordAverageAnswerRate;

    @Column(name = "ox_average_answer_rate", nullable = false, columnDefinition = "DECIMAL(5,2) DEFAULT 0.00")
    private BigDecimal oxAverageAnswerRate;

    @Column(name = "matching_average_answer_rate", nullable = false, columnDefinition = "DECIMAL(5,2) DEFAULT 0.00")
    private BigDecimal matchingAverageAnswerRate;

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