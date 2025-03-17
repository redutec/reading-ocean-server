package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * CmtSystemCode 엔티티
 * 테이블 정보:
 * - 테이블 명: cmt_system_code
 * - 테이블 코멘트: 공통-시스템코드
 */
@Entity
@Table(name = "cmt_system_code")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CmtSystemCode {
    @Id
    @Column(name = "system_code", nullable = false, length = 6, columnDefinition = "char(6)")
    private String systemCode;

    @Column(name = "category_name", nullable = false, length = 30)
    private String categoryName;

    @Column(name = "sub_category_name", nullable = false, length = 30)
    private String subCategoryName;

    @Column(name = "code_name", nullable = false, length = 30)
    private String codeName;

    @Column(name = "code_value", nullable = false, length = 100)
    private String codeValue;

    @Column(name = "reference_value", length = 100)
    private String referenceValue;

    @Column(name = "property_value", length = 100)
    private String propertyValue;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1)")
    private Character useYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", nullable = false, length = 30)
    private String adminId;

    @Column(name = "description", length = 100)
    private String description;
}