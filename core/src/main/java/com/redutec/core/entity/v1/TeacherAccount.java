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
 * TeacherAccount 엔티티
 * 테이블 정보:
 * - 테이블 명: teacher_account
 * - 테이블 코멘트: 교사 계정 정보
 */
@Entity
@Table(name = "teacher_account", uniqueConstraints = {
        @UniqueConstraint(name = "account_id_UNIQUE", columnNames = "account_id")
}, indexes = {
        @Index(name = "idx_teacher_academy_no", columnList = "academy_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class TeacherAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_no", nullable = false, updatable = false)
    private Integer accountNo;

    @Column(name = "account_id", length = 16, nullable = false)
    private String accountId;

    @Column(name = "password", length = 500, nullable = false)
    private String password;

    @Column(name = "password_salt", length = 45, nullable = false)
    private String passwordSalt;

    @Column(name = "account_status", length = 20)
    private String accountStatus;

    @Column(name = "academy_no", nullable = false)
    private Integer academyNo;

    @Column(name = "name", length = 10, nullable = false)
    private String name;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "mobile_no", length = 15, nullable = false)
    private String mobileNo;

    @Column(name = "account_type", length = 15, nullable = false)
    private String accountType;

    @Column(name = "allow_sms_yn", nullable = false, columnDefinition = "char")
    @JdbcTypeCode(Types.CHAR)
    private String allowSmsYn;

    @Column(name = "allow_email_yn", nullable = false, columnDefinition = "char")
    @JdbcTypeCode(Types.CHAR)
    private String allowEmailYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "last_login_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime lastLoginDatetime;

    @Column(name = "change_password_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime changePasswordDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}