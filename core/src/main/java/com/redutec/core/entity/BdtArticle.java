package com.redutec.core.entity;

import com.redutec.core.meta.CategoryValue;
import com.redutec.core.meta.Domain;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * BdtArticle 엔티티
 * 테이블 정보:
 * - 테이블 명: bdt_article
 * - 테이블 코멘트: 게시물
 */
@Entity
@Table(name = "bdt_article")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class BdtArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_no", nullable = false, updatable = false)
    private Integer articleNo;

    @Column(name = "article_board_no", nullable = false)
    private Integer articleBoardNo;

    @Column(name = "category_value", length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    @Enumerated(EnumType.STRING)
    private CategoryValue categoryValue;

    @Column(name = "article_title", length = 100, nullable = false)
    private String articleTitle;

    @Lob
    @Column(name = "article_content", columnDefinition = "LONGTEXT")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String articleContent;

    @Lob
    @Column(name = "article_content_detail", columnDefinition = "TEXT")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String articleContentDetail;

    @Column(name = "display_yn", nullable = false, columnDefinition = "CHAR(1)")
    @JdbcTypeCode(Types.CHAR)
    private String displayYn;

    @Column(name = "display_domain_code", length = 6, nullable = false, columnDefinition = "CHAR(6) DEFAULT 'DMC002'")
    @JdbcTypeCode(Types.CHAR)
    private String displayDomainCode;

    @Column(name = "domain", length = 24)
    @Enumerated(EnumType.STRING)
    private Domain domain;

    @Column(name = "display_write_date", nullable = false, columnDefinition = "DATE DEFAULT (curdate())")
    @CreationTimestamp
    private LocalDate displayWriteDate;

    @Column(name = "top_pin_yn", columnDefinition = "CHAR(1) DEFAULT 'N'")
    @JdbcTypeCode(Types.CHAR)
    private String topPinYn;

    @Column(name = "display_configuration_yn", nullable = false, columnDefinition = "CHAR(1)")
    @JdbcTypeCode(Types.CHAR)
    private String displayConfigurationYn;

    @Column(name = "writer_account_no")
    private Integer writerAccountNo;

    @Column(name = "display_writer_name", length = 30)
    private String displayWriterName;

    @Column(name = "use_yn", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y'")
    @JdbcTypeCode(Types.CHAR)
    @Builder.Default
    private String useYn = "Y";

    @Column(name = "read_count", length = 3, columnDefinition = "VARCHAR(3) DEFAULT '0'")
    private String readCount;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30, nullable = false)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;

    /**
     * BdtArticle 엔티티의 필드를 업데이트하는 도메인 메서드입니다.
     * 각 파라미터가 null이 아닌 경우 해당 필드를 업데이트하며, null인 경우 기존 값이 유지됩니다.
     * 이 메서드는 JPA의 더티 체킹 메커니즘을 활용하여 변경된 필드가 데이터베이스에 반영되도록 합니다.
     *
     * @param categoryValue           null이 아닌 경우 categoryValue 필드를 업데이트합니다.
     * @param articleTitle            null이 아닌 경우 articleTitle 필드를 업데이트합니다.
     * @param articleContent          null이 아닌 경우 articleContent 필드를 업데이트합니다.
     * @param articleContentDetail    null이 아닌 경우 articleContentDetail 필드를 업데이트합니다.
     * @param displayYn               null이 아닌 경우 displayYn 필드를 업데이트합니다.
     * @param domain                  null이 아닌 경우 domain 필드를 업데이트합니다.
     * @param adminId                 null이 아닌 경우 adminId 필드를 업데이트합니다.
     */
    public void updateBdtArticle(
            CategoryValue categoryValue,
            String articleTitle,
            String articleContent,
            String articleContentDetail,
            String displayYn,
            Domain domain,
            String adminId
    ) {
        this.categoryValue = categoryValue != null ? categoryValue : this.categoryValue;
        this.articleTitle = articleTitle != null ? articleTitle : this.articleTitle;
        this.articleContent = articleContent != null ? articleContent : this.articleContent;
        this.articleContentDetail = articleContentDetail != null ? articleContentDetail : this.articleContentDetail;
        this.displayYn = displayYn != null ? displayYn : this.displayYn;
        this.domain = domain != null ? domain : this.domain;
        this.adminId = adminId != null ? adminId : this.adminId;
    }
}