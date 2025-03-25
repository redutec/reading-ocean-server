package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * ReadingGameContestPointRevokeHistory 엔티티
 * 테이블 정보:
 * - 테이블 명: reading_game_contest_point_revoke_history
 * - 테이블 코멘트: 독서게임대회 포인트 회수 내역
 */
@Entity
@Table(name = "reading_game_contest_point_revoke_history")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ReadingGameContestPointRevokeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_game_contest_point_revoke_history_n", nullable = false, updatable = false)
    private Integer readingGameContestPointRevokeHistoryN;

    @Column(name = "reading_game_contest_account_no", nullable = false)
    private Integer readingGameContestAccountNo;

    @Column(name = "domain", nullable = false, length = 20)
    private String domain;

    @Column(name = "academy_no")
    private Integer academyNo;

    @Column(name = "academy_name", length = 100)
    private String academyName;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "account_id", nullable = false, length = 30)
    private String accountId;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "level", nullable = false, length = 20)
    private String level;

    @Column(name = "book_no", nullable = false)
    private Integer bookNo;

    @Column(name = "book_name", length = 100)
    private String bookName;

    @Column(name = "puzzle_point", nullable = false)
    private Integer puzzlePoint;

    @Column(name = "matching_point", nullable = false)
    private Integer matchingPoint;

    @Column(name = "ox_point", nullable = false)
    private Integer oxPoint;

    @Column(name = "quiz_point", nullable = false)
    private Integer quizPoint;

    @Column(name = "puzzle_count", nullable = false)
    private Integer puzzleCount;

    @Column(name = "matching_count", nullable = false)
    private Integer matchingCount;

    @Column(name = "ox_count", nullable = false)
    private Integer oxCount;

    @Column(name = "quiz_count", nullable = false)
    private Integer quizCount;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;
}