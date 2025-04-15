package com.redutec.core.entity.v1;

import com.redutec.core.entity.v1.key.RdtDiagnosisSerialKey;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * RdtDiagnosisSerial 엔티티
 * 테이블 정보:
 * - 테이블 명: rdt_diagnosis_serial
 * - 테이블 코멘트: 독서 진단 채점 시리얼 (생성 방안 결정 필요함)
 */
@Entity
@Table(name = "rdt_diagnosis_serial",
        uniqueConstraints = {
                @UniqueConstraint(name = "UQ_rdt_diagnosis_serial", columnNames = "diagnosis_serial")
        },
        indexes = {
                @Index(name = "idx_rdt_diagnosis_serial_diagnosis_serial_no", columnList = "diagnosis_serial_no")
        }
)
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class RdtDiagnosisSerial {
    @EmbeddedId
    private RdtDiagnosisSerialKey id;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30, nullable = false)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}