package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * ReadingGameContestAccountHistory 엔티티
 * 테이블 정보:
 * - 테이블 명: reading_game_contest_account_history
 * - 테이블 코멘트: 독서 게임 대회 참가자 내역
 */
@Entity
@Table(name = "reading_game_contest_account_history",
        uniqueConstraints = {
                @UniqueConstraint(name = "reading_game_contest_account_history_uk_01",
                        columnNames = {"reading_game_contest_account_no", "book_no", "reading_mission_type"})
        },
        indexes = {
                @Index(name = "idx_reading_game_contest_account_history_account_no", columnList = "reading_game_contest_account_no"),
                @Index(name = "idx_reading_game_contest_account_history_book_no", columnList = "book_no"),
                @Index(name = "idx_reading_game_contest_account_history_mission_history_no", columnList = "mission_history_no")
        }
)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ReadingGameContestAccountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_game_contest_account_history_no", nullable = false, updatable = false)
    private Integer readingGameContestAccountHistoryNo;

    @Column(name = "reading_game_contest_account_no", nullable = false)
    private Integer readingGameContestAccountNo;

    @Column(name = "mission_history_no", nullable = false)
    private Integer missionHistoryNo;

    @Column(name = "book_no", nullable = false)
    private Integer bookNo;

    @Column(name = "reading_mission_type", length = 30, nullable = false)
    private String readingMissionType;

    @Column(name = "answer_rate", nullable = false)
    private Integer answerRate;

    @Column(name = "answer_count", length = 5, nullable = false, columnDefinition = "varchar(5) default '0/0'")
    private String answerCount;

    @Column(name = "point", nullable = false, columnDefinition = "int default 0")
    private Integer point;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_id", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}