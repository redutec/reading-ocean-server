package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * CmtConfigurationGeneral 엔티티
 * 테이블 정보:
 * - 테이블 명: cmt_configuration_general
 * - 테이블 코멘트: 설정 값 일반관리(사이트관리관련 정보,..), 초기 데이터는 빈문자열로 저장 필요
 */
@Entity
@Table(name = "cmt_configuration_general")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CmtConfigurationGeneral {
    @Id
    @Column(name = "configuration_key", length = 50, nullable = false)
    private String configurationKey;

    @Column(name = "configuration_category_key", length = 50, nullable = false)
    private String configurationCategoryKey;

    @Column(name = "configuration_category_name", length = 30, nullable = false)
    private String configurationCategoryName;

    @Column(name = "configuration_name", length = 30, nullable = false)
    private String configurationName;

    @Lob
    @Column(name = "configuration_content", nullable = false, columnDefinition = "text")
    private String configurationContent;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1) default 'Y'")
    private Character useYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30, nullable = false)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}