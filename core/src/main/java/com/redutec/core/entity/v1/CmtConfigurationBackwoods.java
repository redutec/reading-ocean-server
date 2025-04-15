package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;

/**
 * CmtConfigurationBackwoods 엔티티
 * 테이블 정보:
 * - 테이블 명: cmt_configuration_backwoods
 * - 테이블 코멘트: 도서산간 배송정보
 */
@Entity
@Table(name = "cmt_configuration_backwoods")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CmtConfigurationBackwoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "backwoods_no", nullable = false, updatable = false)
    private Integer backwoodsNo;

    @Column(name = "begin_zipcode", nullable = false, length = 5, columnDefinition = "char(5)")
    @JdbcTypeCode(Types.CHAR)
    private String beginZipcode;

    @Column(name = "end_zipcode", nullable = false, length = 5, columnDefinition = "char(5)")
    @JdbcTypeCode(Types.CHAR)
    private String endZipcode;

    @Column(name = "backwoods_region_name", nullable = false, length = 45)
    private String backwoodsRegionName;

    @Column(name = "additional_delivery_fee", nullable = false)
    private Integer additionalDeliveryFee;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1) default 'Y'")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", nullable = false, length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}