package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * CmtProcessLog 엔티티
 * 테이블 정보:
 * - 테이블 명: cmt_process_log
 * - 테이블 코멘트: 처리결과
 */
@Entity
@Table(name = "cmt_process_log")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CmtProcessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_no", nullable = false, updatable = false)
    private Integer processNo;

    @Column(name = "process_step_name", length = 100, nullable = false)
    private String processStepName;

    @Column(name = "process_substep_name", length = 100)
    private String processSubstepName;

    @Column(name = "is_success", nullable = false, columnDefinition = "bit")
    private Boolean isSuccess;

    @Column(name = "result_code", length = 10)
    private String resultCode;

    @Column(name = "result_message", length = 300)
    private String resultMessage;

    @Column(name = "execute_datetime")
    private LocalDateTime executeDatetime;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @Column(name = "admin_ID", length = 20)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}