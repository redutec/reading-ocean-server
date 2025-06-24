package com.redutec.core.entity;

import com.redutec.core.meta.Domain;
import com.redutec.core.meta.PolicyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@DynamicUpdate
@Comment("정책(이용약관, 개인정보처리방침 등)")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("정책 고유번호")
    private Long id;

    @Comment("노출 도메인")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Domain domain;

    @Comment("유형")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PolicyType type;

    @Comment("버전(e.g. v1.0, 2025-05)")
    @Setter
    @NotBlank
    @Column(nullable = false, length = 50)
    private String version;

    @Comment("내용(HTML 혹은 마크다운)")
    @Setter
    @NotBlank
    @Column(nullable = false)
    @Lob
    private String content;

    @Column(nullable = false)
    @Setter
    @Comment("적용 시작일시")
    private LocalDateTime effectiveAt;

    @Column
    @Setter
    @Comment("적용 종료일시(선택)")
    private LocalDateTime expiresAt;

    @Comment("활성화 여부")
    @Setter
    @Column(nullable = false)
    @Builder.Default
    private Boolean available = true;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Comment("등록일시")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    @Comment("수정일시")
    private LocalDateTime updatedAt;
}
