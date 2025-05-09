package com.redutec.core.entity;

import com.redutec.core.config.AesAttributeConverter;
import com.redutec.core.meta.AuthenticationStatus;
import com.redutec.core.meta.TeacherRole;
import com.redutec.core.meta.TeacherStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Comment("교사")
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("교사 고유번호")
    private Long id;

    @Comment("로그인 아이디")
    @Column(length = 20, nullable = false)
    private String accountId;

    @Comment("비밀번호")
    @Column(nullable = false)
    private String password;

    @Comment("교사명(AES256 암호화)")
    @Convert(converter = AesAttributeConverter.class)
    @Column(nullable = false)
    private String name;

    @Comment("연락처(AES256 암호화)")
    @Convert(converter = AesAttributeConverter.class)
    @Column(nullable = false)
    private String phoneNumber;

    @Comment("이메일(AES256 암호화)")
    @Convert(converter = AesAttributeConverter.class)
    @Column
    private String email;

    @Comment("상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TeacherStatus status = TeacherStatus.WAIT;

    @Comment("역할")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TeacherRole role = TeacherRole.TEACHER;

    @Comment("계정 상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AuthenticationStatus authenticationStatus = AuthenticationStatus.INACTIVE;

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

    public void updateTeacher(
            String accountId,
            String password,
            String name,
            String phoneNumber,
            String email,
            TeacherStatus status,
            TeacherRole role,
            AuthenticationStatus authenticationStatus,
            String description,
            Institute institute,
            Homeroom homeroom
    ) {
        this.accountId = Optional.ofNullable(accountId).orElse(this.accountId);
        this.password = Optional.ofNullable(password).orElse(this.password);
        this.name = Optional.ofNullable(name).orElse(this.name);
        this.phoneNumber = Optional.ofNullable(phoneNumber).orElse(this.phoneNumber);
        this.email = Optional.ofNullable(email).orElse(this.email);
        this.status = Optional.ofNullable(status).orElse(this.status);
        this.role = Optional.ofNullable(role).orElse(this.role);
        this.authenticationStatus = Optional.ofNullable(authenticationStatus).orElse(this.authenticationStatus);
        this.description = Optional.ofNullable(description).orElse(this.description);
        this.institute = Optional.ofNullable(institute).orElse(this.institute);
        this.homeroom = Optional.ofNullable(homeroom).orElse(this.homeroom);
    }
}