package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;

/**
 * Kmcis 엔티티
 * 테이블 정보:
 * - 테이블 명: kmcis
 * - 테이블 코멘트: 인증내역
 */
@Entity
@Table(name = "kmcis")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Kmcis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kmcis_no", nullable = false, updatable = false)
    private Integer kmcisNo;

    @Column(name = "cert_num", length = 100, nullable = false)
    private String certNum;

    @Lob
    @Column(name = "body", nullable = false, columnDefinition = "text")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String body;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;
}