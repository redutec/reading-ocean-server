// o.server.core.entity.AuthenticationHistory.java
package com.redutec.core.entity;

import com.redutec.core.meta.AuthenticationEventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Comment("인증 이력(어드민)")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class AdminAuthenticationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("인증 이력 ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "admin_user_id", nullable = false)
    private AdminUser adminUser;

    @Comment("인증 유형(LOGIN_SUCCESS, LOGIN_FAILURE, LOGOUT, PASSWORD_CHANGE, PASSWORD_RESET, USERNAME_RECOVERY, TOKEN_REFRESH)")
    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private AuthenticationEventType eventType;

    @Comment("클라이언트 IP 주소")
    @Column(length = 50)
    private String clientIp;

    @Comment("User-Agent 헤더 값")
    @Column(length = 250)
    private String userAgent;

    @CreatedDate
    @Comment("생성일시")
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}