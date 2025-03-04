package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bot_user",
        uniqueConstraints = {@UniqueConstraint(name = "UQ_bot_user_id", columnNames = "user_ID")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BotUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no", nullable = false, updatable = false)
    private Integer userNo;

    @Column(name = "user_ID", length = 100, nullable = false, unique = true)
    private String userId;

    @Column(name = "user_name", length = 20, nullable = false)
    private String userName;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "password_salt_value", length = 100, nullable = false)
    private String passwordSaltValue;

    @Column(name = "use_yn", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y'")
    private Character useYn;

    @Column(name = "last_access_IP", length = 100)
    private String lastAccessIp;

    @Column(name = "last_access_datetime")
    private LocalDateTime lastAccessDatetime;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BotUserGroup> userGroups;
}