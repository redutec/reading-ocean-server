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
 * MltMallProductImage 엔티티
 * 테이블 정보:
 * - 테이블 명: mlt_mall_product_image
 * - 테이블 코멘트: 온라인 상품 이미지
 */
@Entity
@Table(name = "mlt_mall_product_image")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MltMallProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_no", nullable = false, updatable = false)
    private Integer productImageNo;

    @Column(name = "product_no", nullable = false)
    private Integer productNo;

    @Column(name = "thumbnail_yn", nullable = false, columnDefinition = "char")
    @JdbcTypeCode(Types.CHAR)
    private String thumbnailYn;

    @Column(name = "base_image_file_name", length = 100)
    private String baseImageFileName;

    @Column(name = "base_image_path", length = 300, nullable = false)
    private String baseImagePath;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char")
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
}