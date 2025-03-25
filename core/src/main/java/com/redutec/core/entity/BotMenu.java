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
 * BotMenu 엔티티
 * 테이블 정보:
 * - 테이블 명: bot_menu
 * - 테이블 코멘트: 백오피스 - 메뉴
 */
@Entity
@Table(name = "bot_menu")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class BotMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_no", nullable = false, updatable = false)
    private Integer menuNo;

    @Column(name = "menu_name", length = 100, nullable = false)
    private String menuName;

    @Column(name = "menu_Key", length = 30)
    private String menuKey;

    @Column(name = "url", length = 300)
    private String url;

    @Column(name = "parent_menu_no")
    private Integer parentMenuNo;

    @Column(name = "display_order")
    private Byte displayOrder;

    @Column(name = "use_yn", nullable = false, columnDefinition = "CHAR(1)")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 20, nullable = false)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}