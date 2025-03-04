package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bot_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Character useYn;

    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT NOW()")
    @CreatedDate
    private LocalDateTime registerDatetime;

    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT NOW() ON UPDATE CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 20)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BotUserGroup> userGroups;
}