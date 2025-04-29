package com.redutec.core.entity;

import com.redutec.core.config.AesAttributeConverter;
import com.redutec.core.meta.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Comment("학생")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("학생 고유번호")
    private Long id;

    @Comment("로그인 아이디")
    @Column(length = 20, nullable = false)
    private String accountId;

    @Comment("비밀번호")
    @Column(nullable = false)
    private String password;

    @Comment("이름(AES256 암호화)")
    @Convert(converter = AesAttributeConverter.class)
    @Column(nullable = false)
    private String name;

    @Comment("연락처(AES256 암호화)")
    @Convert(converter = AesAttributeConverter.class)
    @Column
    private String phoneNumber;

    @Comment("이메일(AES256 암호화)")
    @Convert(converter = AesAttributeConverter.class)
    @Column
    private String email;

    @Comment("생년월일")
    @Column(length = 8)
    @Pattern(regexp = "^[0-9]{8}$", message = "생년월일은 8자리의 숫자로만 구성되어야 합니다.")
    private String birthday;

    @Comment("상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StudentStatus status = StudentStatus.WAIT;

    @Comment("계정 상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AuthenticationStatus authenticationStatus = AuthenticationStatus.INACTIVE;

    @Comment("독서 레벨")
    @Column
    @Enumerated(EnumType.STRING)
    private ReadingLevel readingLevel;

    @Comment("실제 RAQ")
    @Column
    private Integer raq;

    @Comment("학년")
    @Column
    @Enumerated(EnumType.STRING)
    private SchoolGrade schoolGrade;

    @Comment("도서 포인트")
    @Column
    @PositiveOrZero
    private Integer bookPoints;

    @Comment("도서 MBTI")
    @Column
    @Enumerated(EnumType.STRING)
    private BookMbti bookMbti;

    @Comment("마지막 로그인 IP")
    @Column(length = 45)
    @Setter
    private String lastLoginIp;

    @Comment("마지막 로그인 일시")
    @Column
    @Setter
    private LocalDateTime lastLoginAt;

    @Comment("비고")
    @Column
    private String description;

    @Comment("서비스 도메인")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Domain domain;

    @Comment("소속 교육기관")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Institute institute;

    @Comment("소속 학급")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private InstituteClass instituteClass;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void updateStudent(
            String accountId,
            String password,
            String name,
            String phoneNumber,
            String email,
            String birthday,
            StudentStatus status,
            AuthenticationStatus authenticationStatus,
            ReadingLevel readingLevel,
            Integer raq,
            SchoolGrade schoolGrade,
            Integer bookPoints,
            String description,
            Domain domain,
            Institute institute,
            InstituteClass instituteClass
    ) {
        this.accountId = accountId != null ? accountId : this.accountId;
        this.password = password != null ? password : this.password;
        this.name = name != null ? name : this.name;
        this.phoneNumber = phoneNumber != null ? phoneNumber : this.phoneNumber;
        this.email = email != null ? email : this.email;
        this.birthday = birthday != null ? birthday : this.birthday;
        this.status = status != null ? status : this.status;
        this.authenticationStatus = authenticationStatus != null ? authenticationStatus : this.authenticationStatus;
        this.readingLevel = readingLevel != null ? readingLevel : this.readingLevel;
        this.raq = raq != null ? raq : this.raq;
        this.schoolGrade = schoolGrade != null ? schoolGrade : this.schoolGrade;
        this.bookPoints = bookPoints != null ? bookPoints : this.bookPoints;
        this.description = description != null ? description : this.description;
        this.domain = domain != null ? domain : this.domain;
        this.institute = institute != null ? institute : this.institute;
        this.instituteClass = instituteClass != null ? instituteClass : this.instituteClass;
    }
}