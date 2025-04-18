package com.redutec.core.entity;

import com.redutec.core.meta.AdministratorRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 어드민 메뉴 정보를 저장하는 엔티티 클래스입니다.
 * 이 클래스는 데이터베이스의 `admin_menu` 테이블과 매핑됩니다.
 */
@Entity
@Comment("어드민 메뉴")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AdminMenu {
    @Comment("어드민 메뉴 고유번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("메뉴명")
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Comment("접근 URL")
    @Column(nullable = false, unique = true, length = 30)
    private String url;

    @Comment("비고")
    @Column(nullable = false)
    private String description;

    @Comment("사용여부")
    @Column(nullable = false)
    private Boolean available;

    @Comment("접근 가능 권한")
    @ElementCollection(fetch = FetchType.EAGER, targetClass = AdministratorRole.class)
    @CollectionTable(name = "admin_menu_roles", joinColumns = @JoinColumn(name = "admin_menu_id"))
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private List<AdministratorRole> accessibleRoles;

    @Comment("메뉴의 깊이")
    @Column(nullable = false)
    private Integer depth;

    @Comment("상위 메뉴")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private AdminMenu parent;

    @Comment("소속된 하위 메뉴")
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdminMenu> children;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void updateAdminMenu (
            String name,
            String url,
            String description,
            Boolean available,
            List<AdministratorRole> accessibleRoles,
            Integer depth,
            AdminMenu parent
    ) {
        this.name = name != null ? name : this.name;
        this.url = url != null ? url : this.url;
        this.description = description != null ? description : this.description;
        this.available = available != null ? available : this.available;
        this.accessibleRoles = accessibleRoles != null ? accessibleRoles : this.accessibleRoles;
        this.depth = depth != null ? depth : this.depth;
        this.parent = parent != null ? parent : this.parent;
    }
}