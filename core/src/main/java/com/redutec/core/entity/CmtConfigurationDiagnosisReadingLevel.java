package com.redutec.core.entity;

import com.redutec.core.entity.key.CmtConfigurationDiagnosisReadingLevelKey;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * CmtConfigurationDiagnosisReadingLevel 엔티티
 * 테이블 정보:
 * - 테이블 명: cmt_configuration_diagnosis_reading_level
 * - 테이블 코멘트: 독서진단 구성- 학년별 독서 레벨
 */
@Entity
@Table(name = "cmt_configuration_diagnosis_reading_level")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CmtConfigurationDiagnosisReadingLevel {
    @EmbeddedId
    private CmtConfigurationDiagnosisReadingLevelKey id;

    @Column(name = "minimum_RAQ", nullable = false)
    private Short minimumRAQ;

    @Column(name = "maximum_RAQ", nullable = false)
    private Short maximumRAQ;

    @Column(name = "reading_level_group_code", length = 10)
    private String readingLevelGroupCode;

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