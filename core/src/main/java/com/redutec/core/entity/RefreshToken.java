package com.redutec.core.entity;

import com.redutec.core.meta.Domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Refresh Token 엔티티 클래스.
 * JWT Refresh Token을 데이터베이스에 저장하고 관리합니다.
 */
@Entity
@Comment("JWT Refresh Token")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RefreshToken {
    @Comment("Refresh Token 고유번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("토큰값")
    @Column(nullable = false, unique = true)
    private String token;

    @Comment("토큰 생성 계정")
    @Column(nullable = false)
    private String username;

    @Comment("토큰 생성 도메인")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Domain domain;

    @Comment("만료일")
    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * Refresh Token이 만료되었는지 확인합니다.
     *
     * @return 만료되었으면 true, 그렇지 않으면 false
     */
    public boolean isExpired() {
        return expiresAt.isBefore(LocalDateTime.now());
    }
}