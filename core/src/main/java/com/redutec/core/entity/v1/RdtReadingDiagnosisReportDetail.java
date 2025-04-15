package com.redutec.core.entity.v1;

import com.redutec.core.entity.v1.key.RdtReadingDiagnosisReportDetailKey;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * RdtReadingDiagnosisReportDetail 엔티티
 * 테이블 정보:
 * - 테이블 명: rdt_reading_diagnosis_report_detail
 * - 테이블 코멘트: 독서 진단 결과 리포트
 */
@Entity
@Table(name = "rdt_reading_diagnosis_report_detail")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class RdtReadingDiagnosisReportDetail {
    @EmbeddedId
    private RdtReadingDiagnosisReportDetailKey id;

    @Column(name = "apply_score", nullable = false)
    private Byte applyScore;

    @Column(name = "apply_average_score", nullable = false)
    private Byte applyAverageScore;

    @Column(name = "grade_average_score", nullable = false)
    private Byte gradeAverageScore;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", nullable = false, length = 30)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}