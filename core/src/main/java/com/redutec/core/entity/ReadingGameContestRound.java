package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ReadingGameContestRound 엔티티
 * 테이블 정보:
 * - 테이블 명: reading_game_contest_round
 * - 테이블 코멘트: 독서 게임 대회 회차
 */
@Entity
@Table(name = "reading_game_contest_round", indexes = {
        @Index(name = "idx_reading_game_contest_round_reading_game_contest_no", columnList = "reading_game_contest_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ReadingGameContestRound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_game_contest_round_no", nullable = false, updatable = false)
    private Integer readingGameContestRoundNo;

    @Column(name = "reading_game_contest_no", nullable = false)
    private Integer readingGameContestNo;

    @Column(name = "round_no", nullable = false)
    private Integer roundNo;

    @Column(name = "display_start_date", nullable = false)
    private LocalDate displayStartDate;

    @Column(name = "display_end_date", nullable = false)
    private LocalDate displayEndDate;

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