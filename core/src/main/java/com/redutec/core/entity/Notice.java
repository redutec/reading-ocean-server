package com.redutec.core.entity;

import com.redutec.core.meta.Domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Comment("공지사항")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("공지사항 고유번호")
    private Long id;

    @Comment("노출 도메인")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Domain domain;

    @Comment("제목")
    @Setter
    @Column(nullable = false, length = 100)
    @NotBlank
    private String title;

    @Comment("내용")
    @Setter
    @Column(nullable = false)
    @Lob
    private String content;

    @Comment("첨부 파일명")
    @Setter
    @Column
    private String attachmentFileName;

    @Comment("노출 여부")
    @Setter
    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean visible = true;

    @Comment("노출 시작일시")
    @Setter
    @Column(nullable = false)
    private LocalDateTime visibleStartAt;

    @Comment("노출 종료일시")
    @Setter
    @Column(nullable = false)
    private LocalDateTime visibleEndAt;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}