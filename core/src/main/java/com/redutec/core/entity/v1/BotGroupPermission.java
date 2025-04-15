package com.redutec.core.entity.v1;

import com.redutec.core.entity.v1.key.BotGroupPermissionKey;
import com.redutec.core.meta.PermissionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;

/**
 * BotGroupPermission 엔티티
 * 테이블 정보:
 * - 테이블 명: bot_group_permission
 * - 테이블 코멘트: 백오피스 - 그룹 권한
 */
@Entity
@Table(name = "bot_group_permission")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class BotGroupPermission {
    @EmbeddedId
    private BotGroupPermissionKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupNo")
    @JoinColumn(name = "group_no", nullable = false)
    private BotGroup group;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("menuNo")
    @JoinColumn(name = "menu_no", nullable = false)
    private BotMenu menu;

    @Column(name = "permission_type", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    @Enumerated(EnumType.STRING)
    private PermissionType permissionType;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 20)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}