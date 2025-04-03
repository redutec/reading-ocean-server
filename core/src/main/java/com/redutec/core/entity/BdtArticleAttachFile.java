package com.redutec.core.entity;

import com.redutec.core.meta.AttachFileValue;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;

/**
 * BdtArticleAttachFile 엔티티
 * 테이블 정보:
 * - 테이블 명: bdt_article_attach_file
 * - 테이블 코멘트: 게시물 첨부파일
 */
@Entity
@Table(name = "bdt_article_attach_file")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class BdtArticleAttachFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_attach_file_no", nullable = false, updatable = false)
    private Integer articleAttachFileNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_no", nullable = false)
    private BdtArticle article;

    @Column(name = "attach_file_value", nullable = false, length = 6, columnDefinition = "CHAR(6) DEFAULT 'AFV003'")
    @JdbcTypeCode(Types.CHAR)
    private AttachFileValue attachFileValue;

    @Column(name = "attach_file_name", length = 100)
    private String attachFileName;

    @Column(name = "attachment_file_path", length = 300, nullable = false)
    private String attachmentFilePath;

    @Column(name = "use_yn", nullable = false, columnDefinition = "CHAR(1)")
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