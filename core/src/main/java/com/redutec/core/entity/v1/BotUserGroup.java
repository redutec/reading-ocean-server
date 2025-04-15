package com.redutec.core.entity.v1;

import com.redutec.core.entity.v1.key.BotUserGroupKey;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;

/**
 * BotUserGroup 엔티티
 * 테이블 정보:
 * - 테이블 명: bot_user_group
 * - 테이블 코멘트: 백오피스 - 사용자 그룹
 */
@Entity
@Table(name = "bot_user_group")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class BotUserGroup {
    @EmbeddedId
    private BotUserGroupKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userNo")
    @JoinColumn(name = "user_no", nullable = false)
    private BotUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupNo")
    @JoinColumn(name = "group_no", nullable = false)
    private BotGroup group;

    @Column(name = "use_yn", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y'")
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