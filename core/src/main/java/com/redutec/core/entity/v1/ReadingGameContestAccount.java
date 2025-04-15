package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * ReadingGameContestAccount 엔티티
 * 테이블 정보:
 * - 테이블 명: reading_game_contest_account
 * - 테이블 코멘트: 독서 게임 대회 참가자 계정 목록
 */
@Entity
@Table(name = "reading_game_contest_account",
        uniqueConstraints = {
                @UniqueConstraint(name = "idx_reading_game_contest_account_account_no", columnNames = "account_no")
        },
        indexes = {
                @Index(name = "idx_reading_game_contest_account_level", columnList = "level"),
                @Index(name = "idx_reading_game_contest_account_point", columnList = "point"),
                @Index(name = "idx_reading_game_contest_account_reading_game_contest_no", columnList = "reading_game_contest_no")
        }
)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ReadingGameContestAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_game_contest_account_no", nullable = false, updatable = false)
    private Integer readingGameContestAccountNo;

    @Column(name = "reading_game_contest_no", nullable = false)
    private Integer readingGameContestNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "level", nullable = false, length = 2)
    private String level;

    @Column(name = "point", columnDefinition = "int default 0")
    private Integer point;

    @Column(name = "answer_rate", columnDefinition = "double default 0")
    private Double answerRate;

    @Column(name = "rate_count", columnDefinition = "int default 0")
    private Integer rateCount;

    @Column(name = "game_duration", columnDefinition = "int default 0")
    private Integer gameDuration;

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