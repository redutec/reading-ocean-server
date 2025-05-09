package com.redutec.core.entity;

import com.redutec.core.config.AesAttributeConverter;
import com.redutec.core.meta.BranchStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Comment("지사")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("고유번호")
    private Long id;

    @Comment("로그인 아이디")
    @Column(length = 20, nullable = false, unique = true)
    private String accountId;

    @Comment("비밀번호")
    @Column(nullable = false)
    private String password;

    @Comment("권역")
    @Column(length = 10)
    private String region;

    @Comment("지사명")
    @Column(length = 20, nullable = false)
    private String name;

    @Comment("상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BranchStatus status;

    @Comment("영업 구역")
    @Column
    private String businessArea;

    @Comment("지사장 이름(AES256 암호화)")
    @Convert(converter = AesAttributeConverter.class)
    @Column
    private String managerName;

    @Comment("지사장 연락처(AES256 암호화)")
    @Convert(converter = AesAttributeConverter.class)
    @Column
    private String managerPhoneNumber;

    @Comment("지사장 이메일(AES256 암호화)")
    @Convert(converter = AesAttributeConverter.class)
    @Column
    private String managerEmail;

    @Comment("계약서 파일명")
    @Column
    private String contractFileName;

    @Comment("계약일")
    @Column
    private LocalDate contractDate;

    @Comment("갱신일")
    @Column
    private LocalDate renewalDate;

    @Comment("비고")
    @Column
    private String description;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void updateBranch(
            String accountId,
            String password,
            String region,
            String name,
            BranchStatus status,
            String businessArea,
            String managerName,
            String managerPhoneNumber,
            String managerEmail,
            String contractFileName,
            LocalDate contractDate,
            LocalDate renewalDate,
            String description
    ) {
        this.accountId = Optional.ofNullable(accountId).orElse(this.accountId);
        this.password = Optional.ofNullable(password).orElse(this.password);
        this.region = Optional.ofNullable(region).orElse(this.region);
        this.name = Optional.ofNullable(name).orElse(this.name);
        this.status = Optional.ofNullable(status).orElse(this.status);
        this.businessArea = Optional.ofNullable(businessArea).orElse(this.businessArea);
        this.managerName = Optional.ofNullable(managerName).orElse(this.managerName);
        this.managerPhoneNumber = Optional.ofNullable(managerPhoneNumber).orElse(this.managerPhoneNumber);
        this.managerEmail = Optional.ofNullable(managerEmail).orElse(this.managerEmail);
        this.contractFileName = Optional.ofNullable(contractFileName).orElse(this.contractFileName);
        this.contractDate = Optional.ofNullable(contractDate).orElse(this.contractDate);
        this.renewalDate = Optional.ofNullable(renewalDate).orElse(this.renewalDate);
        this.description = Optional.ofNullable(description).orElse(this.description);
    }
}