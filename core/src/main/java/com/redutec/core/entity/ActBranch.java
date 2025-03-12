package com.redutec.core.entity;

import com.redutec.core.meta.BranchStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ActBranch 엔티티
 * 테이블 정보:
 * - 테이블 명: act_branch
 * - 테이블 코멘트: 지사 (각 지사별 정보 저장)
 */
@Entity
@Table(name = "act_branch", uniqueConstraints = {
        @UniqueConstraint(name = "branch_id", columnNames = "branch_id"),
        @UniqueConstraint(name = "branch_name", columnNames = "branch_name")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ActBranch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_no", nullable = false, updatable = false)
    private Long branchNo;

    @Column(name = "branch_id", length = 16, nullable = false)
    private String branchId;

    @Column(name = "password", length = 500, nullable = false)
    private String password;

    @Column(name = "password_salt", length = 45, nullable = false)
    private String passwordSalt;

    @Column(name = "branch_region", length = 100)
    private String branchRegion;

    @Column(name = "branch_name", length = 10, nullable = false)
    private String branchName;

    @Column(name = "branch_status", nullable = false, columnDefinition = "enum('ACTIVE','INACTIVE') default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private BranchStatus branchStatus;

    @Column(name = "business_area", columnDefinition = "TEXT")
    private String businessArea;

    @Column(name = "branch_manager_name", length = 10)
    private String branchManagerName;

    @Column(name = "branch_manager_phone_number", length = 11)
    private String branchManagerPhoneNumber;

    @Column(name = "branch_manager_email", length = 40)
    private String branchManagerEmail;

    @Column(name = "contract_file_name", length = 255)
    private String contractFileName;

    @Column(name = "contract_date")
    private LocalDate contractDate;

    @Column(name = "renewal_date")
    private LocalDate renewalDate;

    @Column(name = "description", length = 1024)
    private String description;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;
}