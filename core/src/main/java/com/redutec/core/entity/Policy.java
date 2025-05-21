package com.redutec.core.entity;

import com.redutec.core.meta.Domain;
import com.redutec.core.meta.PolicyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("노출 도메인")
    private Domain domain;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Comment("유형")
    private PolicyType type;

    @Column(nullable = false, length = 50)
    @NotBlank
    @Comment("버전(e.g. v1.0, 2025-05)")
    private String version;

    @Lob
    @Column(nullable = false)
    @NotBlank
    @Comment("내용(HTML 혹은 마크다운)")
    private String content;

    @Column(nullable = false)
    @Comment("적용 시작일시")
    private LocalDateTime effectiveAt;

    @Column
    @Comment("적용 종료일시(선택)")
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    @Comment("활성화 여부")
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
