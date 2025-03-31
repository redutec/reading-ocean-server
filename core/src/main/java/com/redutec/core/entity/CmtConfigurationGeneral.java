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
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String configurationContent;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1) default 'Y'")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

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

    public void updateCmtConfigurationGeneral(
            String configurationKey,
            String configurationCategoryKey,
            String configurationCategoryName,
            String configurationName,
            String configurationContent,
            String useYn,
            String description
    ) {
        this.configurationKey = configurationKey != null ? configurationKey : this.configurationKey;
        this.configurationCategoryKey = configurationCategoryKey != null ? configurationCategoryKey : this.configurationCategoryKey;
        this.configurationCategoryName = configurationCategoryName != null ? configurationCategoryName : this.configurationCategoryName;
        this.configurationName = configurationName != null ? configurationName : this.configurationName;
        this.configurationContent = configurationContent != null ? configurationContent : this.configurationContent;
        this.useYn = useYn != null ? useYn : this.useYn;
        this.description = description != null ? description : this.description;
    }
}