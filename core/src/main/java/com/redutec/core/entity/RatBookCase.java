package com.redutec.core.entity;

import com.redutec.core.entity.key.RatBookCaseKey;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.sql.Types;
import java.time.LocalDateTime;

/**
 * RatBookCase 엔티티
 * 테이블 정보:
 * - 테이블 명: rat_book_case
 * - 테이블 코멘트: 책장
 */
@Entity
@Table(name = "rat_book_case")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class RatBookCase {
    @EmbeddedId
    private RatBookCaseKey id;

    @Column(name = "verification_yn", columnDefinition = "char default 'N'")
    @JdbcTypeCode(Types.CHAR)
    private String verificationYn;

    @Column(name = "reading_page_no", nullable = false, columnDefinition = "SMALLINT DEFAULT 0")
    private Short readingPageNo;

    @Column(name = "reading_progress_rate", nullable = false, columnDefinition = "tinyint default 0")
    private Byte readingProgressRate;

    @Column(name = "mission_accumulate_count", nullable = false, columnDefinition = "tinyint default 0")
    private Byte missionAccumulateCount;

    @Column(name = "on_reading_mission_type", length = 20)
    private String onReadingMissionType;

    @Column(name = "mission_performance_rate", nullable = false, columnDefinition = "tinyint default 0")
    private Byte missionPerformanceRate;

    @Column(name = "reading_activity_rate", nullable = false, columnDefinition = "tinyint default 0")
    private Byte readingActivityRate;

    @Column(name = "verification_date", nullable = false, columnDefinition = "datetime default '9999-12-31 00:00:00'")
    private LocalDateTime verificationDate;

    @Column(name = "reading_complete_datetime")
    private LocalDateTime readingCompleteDatetime;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}