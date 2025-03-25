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
 * CttMissionMatchingGameDetail 엔티티
 * 테이블 정보:
 * - 테이블 명: ctt_mission_matching_game_detail
 * - 테이블 코멘트: 매칭게임 상세
 */
@Entity
@Table(name = "ctt_mission_matching_game_detail", indexes = {
        @Index(name = "idx_mission_matching_game_detail_1", columnList = "matching_game_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CttMissionMatchingGameDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_game_detail_no", nullable = false, updatable = false)
    private Integer matchingGameDetailNo;

    @Column(name = "matching_game_no", nullable = false)
    private Integer matchingGameNo;

    @Column(name = "book_no")
    private Integer bookNo;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @Column(name = "matching_game_answer", length = 1200, nullable = false)
    private String matchingGameAnswer;

    @Column(name = "answer_order", nullable = false)
    private Byte answerOrder;

    @Column(name = "image_path", length = 200)
    private String imagePath;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30, nullable = false)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}