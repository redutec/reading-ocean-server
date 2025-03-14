package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * CmtConfigurationDiagnosisRAQRange 엔티티
 * 테이블 정보:
 * - 테이블 명: cmt_configuration_diagnosis_RAQ_range
 * - 테이블 코멘트: 독서진단 구성- RAQ 구간 (진단결과확인시 RAQ 계산할때 사용 ). 독서진단 구성- RAQ 구간 ( 사용 부분 확인 필요)
 */
@Entity
@Table(name = "cmt_configuration_diagnosis_RAQ_range")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CmtConfigurationDiagnosisRAQRange {
    @Id
    @Column(name = "school_grade", length = 15, nullable = false)
    private String schoolGrade;

    @Column(name = "minimum_RAQ", nullable = false)
    private Short minimumRAQ;

    @Column(name = "maximum_RAQ", nullable = false)
    private Short maximumRAQ;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30, nullable = false)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}