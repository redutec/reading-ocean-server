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
 * BdtArticleBoard 엔티티
 * 테이블 정보:
 * - 테이블 명: bdt_article_board
 * - 테이블 코멘트: 게시판
 */
@Entity
@Table(name = "bdt_article_board")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class BdtArticleBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_board_no", nullable = false, updatable = false)
    private Integer articleBoardNo;

    @Column(name = "article_board_type", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String articleBoardType;

    @Column(name = "article_board_name", nullable = false, length = 45)
    private String articleBoardName;

    @Column(name = "article_board_description", nullable = false, length = 45)
    private String articleBoardDescription;

    @Column(name = "use_yn", nullable = false, columnDefinition = "CHAR(1)")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @Column(name = "set_attach_file_yn", nullable = false, columnDefinition = "CHAR(1)")
    @JdbcTypeCode(Types.CHAR)
    private String setAttachFileYn;

    @Column(name = "set_comment_yn", nullable = false, columnDefinition = "CHAR(1)")
    @JdbcTypeCode(Types.CHAR)
    private String setCommentYn;

    @Column(name = "set_category_yn", nullable = false, columnDefinition = "CHAR(1)")
    @JdbcTypeCode(Types.CHAR)
    private String setCategoryYn;

    @Column(name = "set_flag_yn", nullable = false, columnDefinition = "CHAR(1)")
    @JdbcTypeCode(Types.CHAR)
    private String setFlagYn;

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