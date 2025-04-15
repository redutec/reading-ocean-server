package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

/**
 * StudentActiveApply 엔티티
 * 테이블 정보:
 * - 테이블 명: student_active_apply
 * - 테이블 코멘트: 학생 활성화 신청 테이블
 */
@Entity
@Table(name = "student_active_apply", indexes = {
        @Index(name = "idx_academy_no", columnList = "academy_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class StudentActiveApply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no", nullable = false, updatable = false)
    private Long no;

    @Column(name = "account_no", nullable = false)
    private Long accountNo;

    @Column(name = "academy_no", nullable = false)
    private Long academyNo;

    @Column(name = "reason", length = 500, nullable = false)
    private String reason;

    @Column(name = "is_complete", nullable = false, columnDefinition = "tinyint default 0")
    private Byte isComplete;

    @Column(name = "is_cancel", nullable = false, columnDefinition = "tinyint default 0")
    private Byte isCancel;

    @Column(name = "is_approve", nullable = false, columnDefinition = "tinyint default 0")
    private Byte isApprove;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "datetime default (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "datetime default (now()) on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminID;
}