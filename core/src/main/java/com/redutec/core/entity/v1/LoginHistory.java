package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * LoginHistory 엔티티
 * 테이블 정보:
 * - 테이블 명: login_history
 * - 테이블 코멘트: 로그인 이력
 */
@Entity
@Table(name = "login_history", indexes = {
        @Index(name = "idx_login_history_register_datetime", columnList = "register_datetime")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "login_history_no", nullable = false, updatable = false)
    private Integer loginHistoryNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "account_ID", length = 30, nullable = false)
    private String accountID;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "academy_no", nullable = false)
    private Integer academyNo;

    @Column(name = "academy_name", length = 30, nullable = false)
    private String academyName;

    @Column(name = "remote_ip_address", length = 30, nullable = false)
    private String remoteIpAddress;

    @Column(name = "token_key", length = 700, nullable = false)
    private String tokenKey;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30, nullable = false)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}