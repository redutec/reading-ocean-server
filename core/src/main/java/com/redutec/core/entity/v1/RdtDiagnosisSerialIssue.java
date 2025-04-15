package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * RdtDiagnosisSerialIssue 엔티티
 * 테이블 정보:
 * - 테이블 명: rdt_diagnosis_serial_issue
 * - 테이블 코멘트: 독서 진단 채점 시리얼 발급
 */
@Entity
@Table(name = "rdt_diagnosis_serial_issue", indexes = {
        @Index(name = "idx_rdt_diagnosis_serial_issue_minimum_serial_no", columnList = "minimum_serial_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class RdtDiagnosisSerialIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_serial_issue_no", nullable = false, updatable = false)
    private Integer diagnosisSerialIssueNo;

    @Column(name = "issue_count", nullable = false)
    private Short issueCount;

    @Column(name = "use_count", nullable = false, columnDefinition = "SMALLINT DEFAULT 0")
    private Short useCount;

    @Column(name = "minimum_serial_no", nullable = false)
    private Integer minimumSerialNo;

    @Column(name = "maximum_serial_no", nullable = false)
    private Integer maximumSerialNo;

    @Column(name = "serial_expire_datetime")
    private LocalDateTime serialExpireDatetime;

    @Column(name = "issue_description", length = 300, nullable = false)
    private String issueDescription;

    @Column(name = "serial_status", length = 20, nullable = false, columnDefinition = "varchar(20) default (_utf8mb4'SRS001')")
    private String serialStatus;

    @CreatedDate
    @Column(name = "register_datetime", columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT (now()) on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}