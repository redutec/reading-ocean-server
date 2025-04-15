package com.redutec.core.entity.v1;

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
 * RdtReadingDiagnosisApply 엔티티
 * 테이블 정보:
 * - 테이블 명: rdt_reading_diagnosis_apply
 * - 테이블 코멘트: 독서 진단응시
 */
@Entity
@Table(name = "rdt_reading_diagnosis_apply")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class RdtReadingDiagnosisApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_apply_no", nullable = false, updatable = false)
    private Integer diagnosisApplyNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "diagnosis_paper_type", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String diagnosisPaperType;

    @Column(name = "diagnosis_serial_issue_no", nullable = false)
    private Integer diagnosisSerialIssueNo;

    @Column(name = "diagnosis_serial_no", nullable = false)
    private Integer diagnosisSerialNo;

    @Column(name = "school_grade", length = 15)
    private String schoolGrade;

    @Column(name = "apply_date", nullable = false)
    private LocalDate applyDate;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30, nullable = false)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}