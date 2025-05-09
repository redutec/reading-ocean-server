package com.redutec.core.entity;

import com.redutec.core.meta.InstituteManagementType;
import com.redutec.core.meta.InstituteOperationStatus;
import com.redutec.core.meta.InstituteStatus;
import com.redutec.core.meta.InstituteType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
@Comment("교육기관")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Institute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("교육기관 고유번호")
    private Long id;

    @Comment("지점명")
    @Column(nullable = false, unique = true, length = 60)
    private String name;

    @Comment("사업자 등록명")
    @Column(length = 60, unique = true)
    private String businessRegistrationName;

    @Comment("주소")
    @Column(length = 300)
    private String address;

    @Comment("우편번호")
    @Column(length = 5)
    private String zipCode;

    @Comment("연락처")
    @Column(length = 11)
    @Pattern(regexp = "^[0-9]{1,11}$", message = "연락처는 숫자로만 구성되어야 합니다.")
    private String phoneNumber;

    @Comment("홈페이지")
    @Column(length = 100)
    private String url;

    @Comment("네이버 플레이스 URL")
    @Column(length = 60)
    private String naverPlaceUrl;

    @Comment("유형")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InstituteType type;

    @Comment("운영 유형")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InstituteManagementType managementType;

    @Comment("상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private InstituteStatus status = InstituteStatus.WAIT;

    @Comment("운영 상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private InstituteOperationStatus operationStatus = InstituteOperationStatus.PREPARING;

    @Comment("지사")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Branch branch;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void updateInstitute(
            String name,
            String businessRegistrationName,
            String address,
            String zipCode,
            String phoneNumber,
            String url,
            String naverPlaceUrl,
            InstituteType type,
            InstituteManagementType managementType,
            InstituteStatus status,
            InstituteOperationStatus operationStatus,
            Branch branch
    ) {
        this.name = Optional.ofNullable(name).orElse(this.name);
        this.businessRegistrationName = Optional.ofNullable(businessRegistrationName).orElse(this.businessRegistrationName);
        this.address = Optional.ofNullable(address).orElse(this.address);
        this.zipCode = Optional.ofNullable(zipCode).orElse(this.zipCode);
        this.phoneNumber = Optional.ofNullable(phoneNumber).orElse(this.phoneNumber);
        this.url = Optional.ofNullable(url).orElse(this.url);
        this.naverPlaceUrl = Optional.ofNullable(naverPlaceUrl).orElse(this.naverPlaceUrl);
        this.type = Optional.ofNullable(type).orElse(this.type);
        this.managementType = Optional.ofNullable(managementType).orElse(this.managementType);
        this.status = Optional.ofNullable(status).orElse(this.status);
        this.operationStatus = Optional.ofNullable(operationStatus).orElse(this.operationStatus);
        this.branch = Optional.ofNullable(branch).orElse(this.branch);
    }
}