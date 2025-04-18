package com.redutec.core.entity;

import com.redutec.core.config.AesAttributeConverter;
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
 * Administrator 엔티티 클래스는 시스템 내에서 어드민 사용자의 정보를 나타냅니다.
 * 어드민 사용자 정보에는 로그인 정보, 보안 인증 정보, 권한, 계정 상태 등이 포함됩니다.
 */
@Entity
@Comment("어드민 사용자")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Administrator {
    @Comment("어드민 사용자 고유번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("닉네임(로그인에 사용)")
    @Column(length = 20)
    private String nickname;

    @Comment("비밀번호")
    @Column(nullable = false, length = 60)
    @Setter
    private String password;

    @Comment("권한")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AdministratorRole role;

    @Comment("계정 상태")
    @Column(nullable = false, length = 14)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Setter
    private AuthenticationStatus authenticationStatus = AuthenticationStatus.INACTIVE;

    @Comment("이메일")
    @Convert(converter = AesAttributeConverter.class)
    @Column(nullable = false, unique = true)
    private String email;

    @Comment("비밀번호 틀린 횟수")
    @Column(nullable = false)
    @Builder.Default
    @Setter
    private Integer failedLoginAttempts = 0;

    @Comment("최근 접속 IP")
    @Column(length = 45)
    @Setter
    private String lastLoginIp;

    @Comment("최근 접속일")
    @Column
    @Setter
    private LocalDateTime lastLoginAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void updateAdministrator(
            String nickname,
            String password,
            AdministratorRole role,
            AuthenticationStatus authenticationStatus,
            String email,
            Integer failedLoginAttempts,
            String lastLoginIp,
            LocalDateTime lastLoginAt
    ) {
        this.nickname = nickname != null ? nickname : this.nickname;
        this.password = password != null ? password : this.password;
        this.role = role != null ? role : this.role;
        this.authenticationStatus = authenticationStatus != null ? authenticationStatus : this.authenticationStatus;
        this.email = email != null ? email : this.email;
        this.failedLoginAttempts = failedLoginAttempts != null ? failedLoginAttempts : this.failedLoginAttempts;
        this.lastLoginIp = lastLoginIp != null ? lastLoginIp : this.lastLoginIp;
        this.lastLoginAt = lastLoginAt != null ? lastLoginAt : this.lastLoginAt;
    }
}