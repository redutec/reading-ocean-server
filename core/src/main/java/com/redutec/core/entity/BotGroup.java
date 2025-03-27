package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.List;

/**
 * BotGroup 엔티티
 * 테이블 정보:
 * - 테이블 명: bot_group
 * - 테이블 코멘트: 백오피스 - 그룹
 */
@Entity
@Table(name = "bot_group")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class BotGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_no", nullable = false, updatable = false)
    private Integer groupNo;

    @Column(name = "group_name", length = 100, nullable = false)
    private String groupName;

    @Column(name = "group_Key", length = 100)
    private String groupKey;

    @Column(name = "use_yn", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y'")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT NOW() ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 20)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BotUserGroup> userGroups;

    public void updateBotGroup(String groupName, String description, String useYn) {
        this.groupName = groupName;
        this.description = description;
        this.useYn = useYn;
    }
}