package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 테이블 정보:
 * - 테이블 명: blacklisted_token
 * - 테이블 코멘트: 블랙리스트 토큰
 */
@Entity
@Table(name = "blacklisted_token")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class BlacklistedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1024, unique = true, nullable = false)
    private String token;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    private LocalDateTime modifyDatetime;
}