package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ReadingGameContest 엔티티
 * 테이블 정보:
 * - 테이블 명: reading_game_contest
 * - 테이블 코멘트: 독서 게임 대회
 */
@Entity
@Table(name = "reading_game_contest")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ReadingGameContest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_game_contest_no", nullable = false, updatable = false)
    private Integer readingGameContestNo;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "display_type", length = 20, nullable = false)
    private String displayType;

    @Column(name = "game_start_date", nullable = false)
    private LocalDate gameStartDate;

    @Column(name = "game_end_date", nullable = false)
    private LocalDate gameEndDate;

    @Column(name = "display_start_date", nullable = false)
    private LocalDate displayStartDate;

    @Column(name = "display_end_date", nullable = false)
    private LocalDate displayEndDate;

    @Column(name = "pc_image_path", length = 200)
    private String pcImagePath;

    @Column(name = "mobile_image_path", length = 200)
    private String mobileImagePath;

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

    @Column(name = "register_status", length = 20)
    private String registerStatus;

    @Column(name = "pc_image_name", length = 255, nullable = false)
    private String pcImageName;

    @Column(name = "mobile_image_name", length = 255, nullable = false)
    private String mobileImageName;
}