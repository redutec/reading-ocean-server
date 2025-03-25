package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.sql.Types;
import java.time.LocalDateTime;

/**
 * ReadingGameContestLevel 엔티티
 * 테이블 정보:
 * - 테이블 명: reading_game_contest_level
 * - 테이블 코멘트: 독서 게임 대회 레벨 진단지
 */
@Entity
@Table(name = "reading_game_contest_level", indexes = {
        @Index(name = "idx_reading_game_contest_no_and_school_grade", columnList = "reading_game_contest_no, school_grade")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ReadingGameContestLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_game_contest_level_no", nullable = false, updatable = false)
    private Integer readingGameContestLevelNo;

    @Column(name = "reading_game_contest_no", nullable = false)
    private Integer readingGameContestNo;

    @Column(name = "school_grade", nullable = false, length = 15)
    private String schoolGrade;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

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