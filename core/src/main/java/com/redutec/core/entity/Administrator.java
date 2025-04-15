package com.redutec.core.entity;

import com.redutec.core.meta.AdministratorRole;
import com.redutec.core.meta.AuthenticationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Administrator 엔티티 클래스는 시스템 내에서 시스템 관리자의 정보를 나타냅니다.
 * 시스템 관리자 정보에는 로그인 정보, 보안 인증 정보, 권한, 계정 상태 등이 포함됩니다.
 */
@Entity
@Comment("시스템 관리자")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Administrator {
    /**
     * 시스템 관리자 ID를 나타냅니다.
     * - 자동 생성되며, 기본 키로 사용됩니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 시스템 관리자의 이메일 주소를 나타냅니다.
     * - Base64로 암호화되어 저장됩니다.
     * - 이 필드는 null을 허용하지 않으며, 고유(unique)한 값이어야 합니다.
     * - 로그인 아이디로 사용됩니다.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * 시스템 관리자의 비밀번호를 나타냅니다.
     * - 암호화된 값이 저장됩니다.
     * - 이 필드는 null을 허용하지 않으며, 최대 60자까지 입력 가능합니다.
     */
    @Column(nullable = false, length = 60)
    @Setter
    private String password;

    /**
     * 시스템 관리자의 권한을 나타냅니다.
     * - AdministratorRole 열거형으로 관리됩니다.
     * - 이 필드는 null을 허용하지 않습니다.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AdministratorRole role;

    /**
     * 시스템 관리자 계정의 상태를 나타냅니다.
     * - AuthenticationStatus 열거형으로 관리됩니다.
     * - 기본값은 ACTIVE로 설정되며, 이 필드는 null을 허용하지 않습니다.
     */
    @Column(nullable = false, length = 14)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Setter
    private AuthenticationStatus authenticationStatus = AuthenticationStatus.ACTIVE;

    /**
     * 시스템 관리자 사용자의 닉네임을 나타냅니다.
     * - 개인정보 최소화를 고려하여, 실제 이름 대신 사용됩니다.
     * - 이 필드는 선택 사항입니다.
     */
    @Column(length = 50)
    private String nickname;

    /**
     * 시스템 관리자의 로그인 실패 횟수를 나타냅니다.
     * - 보안상의 이유로, 일정 횟수를 초과하면 추가 인증 또는 계정 잠금 등의 조치를 취할 수 있습니다.
     * - 기본값은 0으로 설정됩니다.
     */
    @Column(nullable = false)
    @Builder.Default
    @Setter
    private Integer failedLoginAttempts = 0;

    /**
     * 시스템 관리자의 최근 로그인 IP 주소를 나타냅니다.
     * - IPv6까지 고려하여, 최대 45자까지 저장됩니다.
     */
    @Column(length = 45)
    @Setter
    private String lastLoginIp;

    /**
     * 시스템 관리자의 최근 로그인 일시를 나타냅니다.
     */
    @Column
    @Setter
    private LocalDateTime lastLoginAt;

    /**
     * 시스템 관리자 계정이 처음 생성된 날짜와 시간을 나타냅니다.
     * - 이 값은 데이터베이스에 처음 저장될 때 자동으로 설정되며, 이후 수정할 수 없습니다.
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 시스템 관리자 계정 정보가 마지막으로 수정된 날짜와 시간을 나타냅니다.
     * - 데이터가 변경될 때마다 자동으로 업데이트됩니다.
     */
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void updateAdministrator(
            String email,
            String password,
            AdministratorRole role,
            AuthenticationStatus authenticationStatus,
            String nickname,
            Integer failedLoginAttempts,
            String lastLoginIp,
            LocalDateTime lastLoginAt
    ) {
        this.email = email != null ? email : this.email;
        this.password = password != null ? password : this.password;
        this.role = role != null ? role : this.role;
        this.authenticationStatus = authenticationStatus != null ? authenticationStatus : this.authenticationStatus;
        this.nickname = nickname != null ? nickname : this.nickname;
        this.failedLoginAttempts = failedLoginAttempts != null ? failedLoginAttempts : this.failedLoginAttempts;
        this.lastLoginIp = lastLoginIp != null ? lastLoginIp : this.lastLoginIp;
        this.lastLoginAt = lastLoginAt != null ? lastLoginAt : this.lastLoginAt;
    }
}