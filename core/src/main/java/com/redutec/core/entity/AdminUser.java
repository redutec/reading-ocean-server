package com.redutec.core.entity;

import com.redutec.core.config.AesAttributeConverter;
import com.redutec.core.meta.AdminUserRole;
import com.redutec.core.meta.AuthenticationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * AdminUser 엔티티 클래스는 시스템 내에서 어드민 사용자의 정보를 나타냅니다.
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
public class AdminUser {
    @Comment("어드민 사용자 고유번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("로그인 아이디")
    @Column(length = 20, nullable = false, unique = true)
    private String accountId;

    @Comment("비밀번호")
    @Column(nullable = false, length = 60)
    @Setter
    private String password;

    @Comment("닉네임")
    @Column(length = 20)
    private String nickname;

    @Comment("이메일(AES256 암호화)")
    @Convert(converter = AesAttributeConverter.class)
    @Column(nullable = false, unique = true)
    private String email;

    @Comment("연락처(AES256 암호화)")
    @Convert(converter = AesAttributeConverter.class)
    @Column
    @Pattern(regexp = "^[0-9]{1,11}$", message = "연락처는 숫자로만 구성되어야 합니다.")
    private String phoneNumber;

    @Comment("권한")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AdminUserRole role;

    @Comment("계정 상태")
    @Column(nullable = false, length = 14)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Setter
    private AuthenticationStatus authenticationStatus = AuthenticationStatus.INACTIVE;

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
}