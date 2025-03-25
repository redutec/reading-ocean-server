package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * CttMissionMatchingGame 엔티티
 * 테이블 정보:
 * - 테이블 명: ctt_mission_matching_game
 * - 테이블 코멘트: 매칭게임
 */
@Entity
@Table(name = "ctt_mission_matching_game", indexes = {
        @Index(name = "idx_mission_matching_game_1", columnList = "book_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CttMissionMatchingGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_game_no", nullable = false, updatable = false)
    private Integer matchingGameNo;

    @Column(name = "book_no", nullable = false)
    private Integer bookNo;

    @Column(name = "matching_game_type", nullable = false, length = 6, columnDefinition = "char(6)")
    private String matchingGameType;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1)")
    private Character useYn;

    @Column(name = "matching_game_group_name", nullable = false, length = 30)
    private String matchingGameGroupName;

    @Column(name = "matching_game_sub_group_name", length = 100)
    private String matchingGameSubGroupName;

    @Column(name = "matching_game_quest", nullable = false, length = 200)
    private String matchingGameQuest;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", nullable = false, length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}