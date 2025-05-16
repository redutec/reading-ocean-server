package com.redutec.core.entity;

import com.redutec.core.config.AesAttributeConverter;
import com.redutec.core.meta.Domain;
import com.redutec.core.meta.InquirerType;
import com.redutec.core.meta.InquiryCategory;
import com.redutec.core.meta.InquiryStatus;
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
@Comment("고객문의")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고객문의 고유번호")
    private Long id;

    @Comment("서비스 도메인")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Domain domain;

    @Comment("문의자 구분(학생/교사/비회원)")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InquirerType inquirerType;

    @Comment("문의 유형(예: 리딩오션, 기술지원 등)")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InquiryCategory category;

    @Comment("처리 상태(응답대기/처리중/응답완료/종료)")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InquiryStatus status;

    @Comment("문의자 이메일")
    @Convert(converter = AesAttributeConverter.class)
    private String inquirerEmail;

    @Comment("답변자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private AdminUser responder;

    @Comment("제목")
    @Column(nullable = false, length = 100)
    @NotBlank
    private String title;

    @Comment("내용")
    @Column
    @Lob
    private String content;

    @Comment("답변")
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