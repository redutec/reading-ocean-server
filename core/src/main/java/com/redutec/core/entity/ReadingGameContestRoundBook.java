package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * ReadingGameContestRoundBook 엔티티
 * 테이블 정보:
 * - 테이블 명: reading_game_contest_round_book
 * - 테이블 코멘트: 독서 게임 대회 회차별 책
 */
@Entity
@Table(name = "reading_game_contest_round_book", indexes = {
        @Index(name = "idx_reading_game_contest_round_book_book_no", columnList = "book_no"),
        @Index(name = "idx_reading_game_contest_round_book_level", columnList = "level"),
        @Index(name = "idx_reading_game_contest_round_book_round_no", columnList = "reading_game_contest_round_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ReadingGameContestRoundBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_game_contest_round_book_no", nullable = false, updatable = false)
    private Integer readingGameContestRoundBookNo;

    @Column(name = "reading_game_contest_round_no", nullable = false)
    private Integer readingGameContestRoundNo;

    @Column(name = "book_no", nullable = false)
    private Integer bookNo;

    @Column(name = "level", length = 3)
    private String level;

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

    @Column(name = "disabled", nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean disabled;
}