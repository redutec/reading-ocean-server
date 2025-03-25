package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * MissionUseLog 엔티티
 * 테이블 정보:
 * - 테이블 명: mission_use_log
 * - 테이블 코멘트: 독서미션 응시 로그
 */
@Entity
@Table(name = "mission_use_log", indexes = {
        @Index(name = "idx_mission_use_log_type_register_contest_no", columnList = "type, register_datetime, reading_game_contest_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MissionUseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_use_log_no", nullable = false, updatable = false)
    private Integer missionUseLogNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "book_no", nullable = false)
    private Integer bookNo;

    @Column(name = "type", length = 20)
    private String type;

    @Column(name = "status", length = 20)
    private String status;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @Column(name = "domain", length = 20, nullable = false)
    private String domain;

    @Column(name = "reading_game_contest_no")
    private Integer readingGameContestNo;
}