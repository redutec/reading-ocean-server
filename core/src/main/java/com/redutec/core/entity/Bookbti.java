package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;

/**
 * Bookbti 엔티티
 * 테이블 정보:
 * - 테이블 명: bookbti
 * - 테이블 코멘트: 북BTI
 */
@Entity
@Table(name = "bookbti")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Bookbti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookbti_no", nullable = false, updatable = false)
    private Integer bookbtiNo;

    @Column(name = "title", nullable = false)
    private String title;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_id", length = 30)
    private String adminId;

    @Column(name = "use_yn", columnDefinition = "CHAR(1)")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @Column(name = "description", length = 300)
    private String description;
}