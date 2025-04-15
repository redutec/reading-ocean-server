package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * AccountReadingLevelResult 엔티티
 * 테이블 정보:
 * - 테이블 명: account_reading_level_result
 * - 테이블 코멘트: 사용자 독서 레벨 진단 결과
 */
@Entity
@Table(name = "account_reading_level_result")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class AccountReadingLevelResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_reading_level_result_no", nullable = false, updatable = false)
    private Integer accountReadingLevelResultNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "reading_level_no", nullable = false)
    private Integer readingLevelNo;

    @Column(name = "school_grade", length = 20, nullable = false)
    private String schoolGrade;

    @Column(name = "paper_type", length = 10)
    private String paperType;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_id", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}