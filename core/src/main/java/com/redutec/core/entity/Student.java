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
import java.util.Optional;

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
    private Homeroom homeroom;

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
            BookMbti bookMbti,
            String description,
            Domain domain,
            Institute institute,
            Homeroom homeroom
    ) {
        this.accountId = Optional.ofNullable(accountId).orElse(this.accountId);
        this.password = Optional.ofNullable(password).orElse(this.password);
        this.name = Optional.ofNullable(name).orElse(this.name);
        this.phoneNumber = Optional.ofNullable(phoneNumber).orElse(this.phoneNumber);
        this.email = Optional.ofNullable(email).orElse(this.email);
        this.birthday = Optional.ofNullable(birthday).orElse(this.birthday);
        this.status = Optional.ofNullable(status).orElse(this.status);
        this.authenticationStatus = Optional.ofNullable(authenticationStatus).orElse(this.authenticationStatus);
        this.readingLevel = Optional.ofNullable(readingLevel).orElse(this.readingLevel);
        this.raq = Optional.ofNullable(raq).orElse(this.raq);
        this.schoolGrade = Optional.ofNullable(schoolGrade).orElse(this.schoolGrade);
        this.bookPoints = Optional.ofNullable(bookPoints).orElse(this.bookPoints);
        this.bookMbti = Optional.ofNullable(bookMbti).orElse(this.bookMbti);
        this.description = Optional.ofNullable(description).orElse(this.description);
        this.domain = Optional.ofNullable(domain).orElse(this.domain);
        this.institute = Optional.ofNullable(institute).orElse(this.institute);
        this.homeroom = Optional.ofNullable(homeroom).orElse(this.homeroom);
    }
}