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
    @Enumerated(EnumType.STRING)
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

    /**
     * BdtArticleAttachFile 엔티티의 첨부파일 정보를 업데이트하는 도메인 메서드입니다.
     * 각 파라미터가 null이 아닌 경우 해당 필드를 업데이트하며, null인 경우 기존 값이 유지됩니다.
     * 이 메서드는 JPA의 더티 체킹 메커니즘을 통해 변경된 상태가 데이터베이스에 반영되도록 합니다.
     *
     * @param attachFileValue     null이 아닌 경우 attachFileValue 필드를 업데이트합니다.
     * @param attachFileName      null이 아닌 경우 attachFileName 필드를 업데이트합니다.
     * @param attachmentFilePath  null이 아닌 경우 attachmentFilePath 필드를 업데이트합니다.
     * @param useYn               null이 아닌 경우 useYn 필드를 업데이트합니다.
     * @param adminId             null이 아닌 경우 adminId 필드를 업데이트합니다.
     * @param description         null이 아닌 경우 description 필드를 업데이트합니다.
     */
    public void updateBdtArticleAttachFile(
            AttachFileValue attachFileValue,
            String attachFileName,
            String attachmentFilePath,
            String useYn,
            String adminId,
            String description
    ) {
        this.attachFileValue = attachFileValue != null ? attachFileValue : this.attachFileValue;
        this.attachFileName = attachFileName != null ? attachFileName : this.attachFileName;
        this.attachmentFilePath = attachmentFilePath != null ? attachmentFilePath : this.attachmentFilePath;
        this.useYn = useYn != null ? useYn : this.useYn;
        this.adminId = adminId != null ? adminId : this.adminId;
        this.description = description != null ? description : this.description;
    }
}