package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * BdtArticleComment 엔티티
 * 테이블 정보:
 * - 테이블 명: bdt_article_comment
 * - 테이블 코멘트: 1:1 문의 답변/ 댓글
 */
@Entity
@Table(name = "bdt_article_comment")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class BdtArticleComment {
    @Id
    @Column(name = "article_no", nullable = false, updatable = false)
    private Integer articleNo;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "article_no", nullable = false)
    private BdtArticle article;

    @Column(name = "reply_complete_yn", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private Character replyCompleteYn;

    @Lob
    @Column(name = "reply_content", nullable = false, columnDefinition = "TEXT")
    private String replyContent;

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