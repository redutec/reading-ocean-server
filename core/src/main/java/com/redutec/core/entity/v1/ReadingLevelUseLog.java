package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * ReadingLevelUseLog 엔티티
 * 테이블 정보:
 * - 테이블 명: reading_level_use_log
 * - 테이블 코멘트: 독서레벨 사용 로그
 */
@Entity
@Table(name = "reading_level_use_log")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ReadingLevelUseLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_level_use_log_no", nullable = false, updatable = false)
    private Integer readingLevelUseLogNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "status", length = 20)
    private String status;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @Column(name = "subscription_no")
    private Integer subscriptionNo;
}