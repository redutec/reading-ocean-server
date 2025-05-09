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
import java.util.Optional;

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

    @Comment("문의자 로그인 아이디(STUDENT 또는 TEACHER)")
    @Column(length = 20)
    private String inquirerAccountId;

    @Comment("문의자 이메일(비로그인 문의자 전용)(AES256 암호화)")
    @Convert(converter = AesAttributeConverter.class)
    @Column
    private String guestEmail;

    @Comment("답변자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private AdminUser adminUser;

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

    public void updateInquiry(
            Domain domain,
            InquirerType inquirerType,
            InquiryCategory category,
            InquiryStatus status,
            String inquirerAccountId,
            String guestEmail,
            AdminUser adminUser,
            String title,
            String content,
            String response
    ) {
        this.domain = Optional.ofNullable(domain).orElse(this.domain);
        this.inquirerType = Optional.ofNullable(inquirerType).orElse(this.inquirerType);
        this.category = Optional.ofNullable(category).orElse(this.category);
        this.status = Optional.ofNullable(status).orElse(this.status);
        this.inquirerAccountId = Optional.ofNullable(inquirerAccountId).orElse(this.inquirerAccountId);
        this.guestEmail = Optional.ofNullable(guestEmail).orElse(this.guestEmail);
        this.adminUser = Optional.ofNullable(adminUser).orElse(this.adminUser);
        this.title = Optional.ofNullable(title).orElse(this.title);
        this.content = Optional.ofNullable(content).orElse(this.content);
        this.response = Optional.ofNullable(response).orElse(this.response);
    }
}