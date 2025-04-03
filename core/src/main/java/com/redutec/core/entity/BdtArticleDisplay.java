package com.redutec.core.entity;

import com.redutec.core.meta.BannerType;
import com.redutec.core.meta.PopupPositionType;
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
    @Enumerated(EnumType.STRING)
    private PopupPositionType popupPositionType;

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
     * 이 메서드는 BdtArticleDisplay 엔티티의 노출 설정 정보를 업데이트합니다.
     * JPA의 더티 체킹 메커니즘을 활용하여 엔티티 상태의 변경을 감지하고 업데이트를 수행합니다.
     * 각 파라미터가 null이 아닌 경우 해당 필드를 업데이트하며, null인 경우 기존 값이 유지됩니다.
     *
     * @param displayBeginDatetime null이 아닌 경우 displayBeginDatetime 필드를 업데이트합니다.
     * @param displayEndDatetime   null이 아닌 경우 displayEndDatetime 필드를 업데이트합니다.
     * @param displayNewWindowYn   null이 아닌 경우 displayNewWindowYn 필드를 업데이트합니다.
     * @param linkURL              null이 아닌 경우 linkURL 필드를 업데이트합니다.
     * @param displayOrder         null이 아닌 경우 displayOrder 필드를 업데이트합니다.
     * @param textColor            null이 아닌 경우 textColor 필드를 업데이트합니다.
     * @param backgroundColor      null이 아닌 경우 backgroundColor 필드를 업데이트합니다.
     * @param bannerType           null이 아닌 경우 bannerType 필드를 업데이트합니다.
     * @param useYn                null이 아닌 경우 useYn 필드를 업데이트합니다.
     * @param adminId              null이 아닌 경우 adminId 필드를 업데이트합니다.
     */
    public void updateBdtArticleDisplay(
            LocalDateTime displayBeginDatetime,
            LocalDateTime displayEndDatetime,
            String displayNewWindowYn,
            String linkURL,
            Byte displayOrder,
            String textColor,
            String backgroundColor,
            BannerType bannerType,
            String useYn,
            String adminId
    ) {
        this.displayBeginDatetime = displayBeginDatetime != null ? displayBeginDatetime : this.displayBeginDatetime;
        this.displayEndDatetime = displayEndDatetime != null ? displayEndDatetime : this.displayEndDatetime;
        this.displayNewWindowYn = displayNewWindowYn != null ? displayNewWindowYn : this.displayNewWindowYn;
        this.linkURL = linkURL != null ? linkURL : this.linkURL;
        this.displayOrder = displayOrder != null ? displayOrder : this.displayOrder;
        this.textColor = textColor != null ? textColor : this.textColor;
        this.backgroundColor = backgroundColor != null ? backgroundColor : this.backgroundColor;
        this.bannerType = bannerType != null ? bannerType : this.bannerType;
        this.useYn = useYn != null ? useYn : this.useYn;
        this.adminId = adminId != null ? adminId : this.adminId;
    }
}