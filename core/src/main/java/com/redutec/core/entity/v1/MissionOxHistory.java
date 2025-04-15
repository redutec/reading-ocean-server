package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * MissionOxHistory 엔티티
 * 테이블 정보:
 * - 테이블 명: mission_ox_history
 * - 테이블 코멘트: OX퀴즈 히스토리 No
 */
@Entity
@Table(name = "mission_ox_history", indexes = {
        @Index(name = "idx_mission_ox_history", columnList = "mission_history_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MissionOxHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ox_history_no", nullable = false, updatable = false)
    private Integer oxHistoryNo;

    @Column(name = "mission_history_no", nullable = false)
    private Integer missionHistoryNo;

    @Column(name = "ox_quiz_no", nullable = false)
    private Integer oxQuizNo;

    @Column(name = "correct", nullable = false, columnDefinition = "tinyint(1)")
    private Boolean correct;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}