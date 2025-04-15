package com.redutec.core.entity.v1;

import com.redutec.core.entity.v1.key.RatReadingMissionResultKey;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDateTime;

/**
 * RatReadingMissionResult 엔티티
 * 테이블 정보:
 * - 테이블 명: rat_reading_mission_result
 * - 테이블 코멘트: 독서미션 결과(최초 응시시 INSERT, 재응시시 UPDATE - 응시 완료시, "도서포인트 * 미션정답률 * 지급비율" 결과 값을 독서포인트 지급 처리 & 미션수행률 처리-트랜잭션 묶음)
 */
@Entity
@Table(name = "rat_reading_mission_result")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class RatReadingMissionResult {
    @EmbeddedId
    private RatReadingMissionResultKey id;

    @Column(name = "apply_count", nullable = false)
    private Integer applyCount;

    @Column(name = "first_apply_datetime", nullable = false)
    private LocalDateTime firstApplyDatetime;

    @Column(name = "last_apply_datetime", nullable = false)
    private LocalDateTime lastApplyDatetime;

    @Column(name = "mission_answer_rate", nullable = false, columnDefinition = "DECIMAL(5,2) DEFAULT 1.00")
    private BigDecimal missionAnswerRate;

    @Column(name = "charge_book_point_yn", nullable = false, columnDefinition = "char default 'N'")
    @JdbcTypeCode(Types.CHAR)
    private String chargeBookPointYn;

    @Column(name = "charge_book_point_amount", nullable = false, columnDefinition = "int default 0")
    private Integer chargeBookPointAmount;

    @Column(name = "mission_answer_information", length = 5)
    private String missionAnswerInformation;

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