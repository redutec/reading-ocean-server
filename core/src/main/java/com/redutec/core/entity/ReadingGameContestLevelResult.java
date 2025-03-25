package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * ReadingGameContestLevelResult 엔티티
 * 테이블 정보:
 * - 테이블 명: reading_game_contest_level_result
 * - 테이블 코멘트: 독서 게임 대회 레벨 진단 결과지
 */
@Entity
@Table(name = "reading_game_contest_level_result")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ReadingGameContestLevelResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_game_contest_level_result_no", nullable = false, updatable = false)
    private Integer readingGameContestLevelResultNo;

    @Column(name = "reading_game_contest_no", nullable = false)
    private Integer readingGameContestNo;

    @Column(name = "school_grade", nullable = false, length = 15)
    private String schoolGrade;

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