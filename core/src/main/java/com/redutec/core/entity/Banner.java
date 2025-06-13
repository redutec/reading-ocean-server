package com.redutec.core.entity;

import com.redutec.core.meta.Domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Comment("배너")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("배너 고유번호")
    private Long id;

    @Comment("노출 도메인")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Domain domain;

    @Comment("제목")
    @Column(nullable = false, length = 100)
    @NotBlank
    private String title;

    @Comment("내용")
    @Column
    @Lob
    private String content;

    @Comment("링크 URL")
    @Column(length = 300)
    @URL
    private String linkUrl;

    @Comment("첨부 파일명")
    @Column
    private String attachmentFileName;

    @Comment("우선순위(0이 최상위)")
    @Column(nullable = false)
    @Min(0)
    @PositiveOrZero
    private Integer priority;

    @Comment("노출 여부")
    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean visible = true;

    @Comment("노출 시작일시")
    @Column(nullable = false)
    private LocalDateTime visibleStartAt;

    @Comment("노출 종료일시")
    @Column(nullable = false)
    private LocalDateTime visibleEndAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}