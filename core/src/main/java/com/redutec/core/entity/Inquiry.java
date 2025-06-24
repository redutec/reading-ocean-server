package com.redutec.core.entity;

import com.redutec.core.meta.Domain;
import com.redutec.core.meta.InquiryCategory;
import com.redutec.core.meta.InquiryStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고객문의 고유번호")
    private Long id;

    @Comment("서비스 도메인")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Domain domain;

    @Comment("문의 유형(예: 리딩오션, 기술지원 등)")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InquiryCategory category;

    @Comment("처리 상태(응답대기/처리중/응답완료/종료)")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InquiryStatus status;

    @Comment("답변자")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private AdminUser responder;

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

    @Comment("답변")
    @Setter
    @Column
    @Lob
    private String response;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}