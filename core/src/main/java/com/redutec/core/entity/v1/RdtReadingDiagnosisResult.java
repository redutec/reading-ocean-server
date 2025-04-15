package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.sql.Types;
import java.time.LocalDateTime;

/**
 * RdtReadingDiagnosisResult 엔티티
 * 테이블 정보:
 * - 테이블 명: rdt_reading_diagnosis_result
 * - 테이블 코멘트: 독서 진단응시 결과
 */
@Entity
@Table(name = "rdt_reading_diagnosis_result")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class RdtReadingDiagnosisResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_apply_result_no", nullable = false, updatable = false)
    private Integer diagnosisApplyResultNo;

    @Column(name = "diagnosis_apply_no", nullable = false)
    private Integer diagnosisApplyNo;

    @Column(name = "diagnosis_subcategory_value", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String diagnosisSubcategoryValue;

    @Column(name = "diagnosis_answer_count", nullable = false)
    private Byte diagnosisAnswerCount;

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