package com.redutec.core.entity;

import com.redutec.core.entity.key.RdtReadingDiagnosisReportCommentKey;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.sql.Types;
import java.time.LocalDateTime;

/**
 * RdtReadingDiagnosisReportComment 엔티티
 * 테이블 정보:
 * - 테이블 명: rdt_reading_diagnosis_report_comment
 * - 테이블 코멘트: 독서 진단 결과 총평
 */
@Entity
@Table(name = "rdt_reading_diagnosis_report_comment")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class RdtReadingDiagnosisReportComment {
    @EmbeddedId
    private RdtReadingDiagnosisReportCommentKey id;

    @Column(name = "diagonsis_level_type", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String diagonsisLevelType;

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