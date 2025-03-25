package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * RdtReadingDiagnosisReport 엔티티
 * 테이블 정보:
 * - 테이블 명: rdt_reading_diagnosis_report
 * - 테이블 코멘트: 독서 진단 결과 리포트
 */
@Entity
@Table(name = "rdt_reading_diagnosis_report")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class RdtReadingDiagnosisReport {
    @Id
    @Column(name = "diagnosis_apply_no", nullable = false, updatable = false)
    private Integer diagnosisApplyNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "diagnosis_paper_type", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String diagnosisPaperType;

    @Column(name = "school_grade", length = 15)
    private String schoolGrade;

    @Column(name = "apply_date", nullable = false)
    private LocalDate applyDate;

    @Column(name = "reading_level", length = 10)
    private String readingLevel;

    @Column(name = "diagnosis_RAQ", nullable = false)
    private Short diagnosisRAQ;

    @Column(name = "apply_score", nullable = false)
    private Byte applyScore;

    @Column(name = "apply_averrage_score", nullable = false)
    private Byte applyAverrageScore;

    @Column(name = "grade_average_score", nullable = false)
    private Byte gradeAverageScore;

    @Column(name = "diagnosis_image_path", length = 200)
    private String diagnosisImagePath;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char default 'Y'")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", nullable = false, length = 30)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}