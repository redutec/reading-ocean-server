package com.redutec.core.entity;

import com.redutec.core.config.AesAttributeConverter;
import com.redutec.core.meta.InstituteManagementType;
import com.redutec.core.meta.InstituteOperationStatus;
import com.redutec.core.meta.InstituteStatus;
import com.redutec.core.meta.InstituteType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Setter
    @Column(nullable = false, unique = true, length = 60)
    private String name;

    @Comment("사업자 등록명")
    @Setter
    @Column(length = 60, unique = true)
    private String businessRegistrationName;

    @Comment("주소")
    @Setter
    @Column(length = 300)
    private String address;

    @Comment("우편번호")
    @Setter
    @Column(length = 5)
    @Pattern(regexp = "^[0-9]{5}", message = "우편번호는 5자리의 숫자로만 구성되어야 합니다.")
    private String postalCode;

    @Comment("연락처(AES256 암호화)")
    @Setter
    @Convert(converter = AesAttributeConverter.class)
    @Column
    @Pattern(regexp = "^[0-9]{1,11}$", message = "연락처는 숫자로만 구성되어야 합니다.")
    private String phoneNumber;

    @Comment("홈페이지")
    @Setter
    @Column(length = 100)
    private String url;

    @Comment("네이버 플레이스 URL")
    @Setter
    @Column(length = 60)
    private String naverPlaceUrl;

    @Comment("유형")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InstituteType type;

    @Comment("운영 유형")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private InstituteManagementType managementType;

    @Comment("상태")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private InstituteStatus status = InstituteStatus.WAIT;

    @Comment("운영 상태")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private InstituteOperationStatus operationStatus = InstituteOperationStatus.PREPARING;

    @Comment("지사")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Branch branch;

    @Comment("소속 교사들")
    @OneToMany(mappedBy = "institute", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Teacher> teachers = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}