package com.redutec.core.entity;

import com.redutec.core.meta.BannerType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;

/**
 * BdtArticleDisplay 엔티티
 * 테이블 정보:
 * - 테이블 명: bdt_article_display
 * - 테이블 코멘트: 게시물 노출설정
 */
@Entity
@Table(name = "bdt_article_display")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class BdtArticleDisplay {
    @Id
    @Column(name = "article_no", nullable = false, updatable = false)
    private Integer articleNo;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "article_no", nullable = false)
    private BdtArticle article;

    @Column(name = "check_no_show_more_yn", columnDefinition = "CHAR(1)")
    @JdbcTypeCode(Types.CHAR)
    private String checkNoShowMoreYn;

    @Column(name = "display_begin_datetime", nullable = false)
    private LocalDateTime displayBeginDatetime;

    @Column(name = "display_end_datetime", nullable = false)
    private LocalDateTime displayEndDatetime;

    @Column(name = "display_new_window_yn", nullable = false, columnDefinition = "CHAR(1)")
    @JdbcTypeCode(Types.CHAR)
    private String displayNewWindowYn;

    @Column(name = "link_URL", length = 300, nullable = false)
    private String linkURL;

    @Column(name = "display_order", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private Byte displayOrder;

    @Column(name = "text_color", length = 10)
    private String textColor;

    @Column(name = "background_color", length = 30)
    private String backgroundColor;

    @Column(name = "banner_type", length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    @Enumerated(EnumType.STRING)
    private BannerType bannerType;

    @Column(name = "popup_position_type", length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String popupPositionType;

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