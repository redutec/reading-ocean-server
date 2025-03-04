package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "bot_user_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Character useYn;

    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    @CreatedDate
    private LocalDateTime registerDatetime;

    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT NOW() ON UPDATE CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 20, nullable = false)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}