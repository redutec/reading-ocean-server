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
 * CmtConfigurationKeyword 엔티티
 * 테이블 정보:
 * - 테이블 명: cmt_configuration_keyword
 * - 테이블 코멘트: 도서 주제어
 */
@Entity
@Table(name = "cmt_configuration_keyword")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CmtConfigurationKeyword {
    @Id
    @Column(name = "keyword", length = 20, nullable = false)
    private String keyword;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1) default 'Y'")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30, nullable = false)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}