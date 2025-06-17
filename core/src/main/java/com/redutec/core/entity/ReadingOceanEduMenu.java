package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 리딩오션 에듀 메뉴 정보를 저장하는 엔티티 클래스입니다.
 * 이 클래스는 데이터베이스의 `reading_ocean_edu_menu` 테이블과 매핑됩니다.
 */
@Entity
@Comment("리딩오션 에듀 메뉴")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ReadingOceanEduMenu {
    @Comment("리딩오션 에듀 메뉴 고유번호")
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

    @Comment("메뉴의 깊이")
    @Column(nullable = false)
    private Integer depth;

    @Comment("상위 메뉴")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private ReadingOceanEduMenu parent;

    @Comment("소속된 하위 메뉴")
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadingOceanEduMenu> children;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}