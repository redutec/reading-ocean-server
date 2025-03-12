package com.redutec.core.entity;

import com.redutec.core.meta.AcademyManageType;
import com.redutec.core.meta.AcademyOperationStatus;
import com.redutec.core.meta.AcademyStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * ActAcademy 엔티티
 * 테이블 정보:
 * - 테이블 명: act_academy
 * - 테이블 코멘트: 학원 (학원장 계정 가입시 함께 생성)
 */
@Entity
@Table(name = "act_academy")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ActAcademy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "academy_no", nullable = false, updatable = false)
    private Integer academyNo;

    @Column(name = "academy_name", length = 30, nullable = false)
    private String academyName;

    @Column(name = "zipcode", length = 5, nullable = false, columnDefinition = "char(5)")
    private String zipcode;

    @Column(name = "academy_address", length = 100, nullable = false)
    private String academyAddress;

    @Column(name = "academy_address_detail", length = 100)
    private String academyAddressDetail;

    @Column(name = "academy_phone_no", length = 15)
    private String academyPhoneNo;

    @Column(name = "academy_manage_type", length = 6, nullable = false, columnDefinition = "char(6)")
    @Enumerated(EnumType.STRING)
    private AcademyManageType academyManageType;

    @Column(name = "academy_url", length = 100)
    private String academyUrl;

    @Column(name = "branch_name", length = 30)
    @Builder.Default
    private String branchName = "없음";

    @Column(name = "academy_status", length = 15)
    @Enumerated(EnumType.STRING)
    private AcademyStatus academyStatus;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;

    @Column(name = "academy_operation_status", nullable = false, columnDefinition = "enum('OPERATING','CLOSED','PREPARING','RESERVED') default 'OPERATING'")
    @Enumerated(EnumType.STRING)
    private AcademyOperationStatus academyOperationStatus;

    @Column(name = "branch_no")
    private Long branchNo;
}