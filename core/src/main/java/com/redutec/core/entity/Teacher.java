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
    @Setter
    @Column(length = 20, nullable = false, unique = true)
    private String accountId;

    @Comment("비밀번호")
    @Setter
    @Column(nullable = false)
    private String password;

    @Comment("교사명(AES256 암호화)")
    @Setter
    @Convert(converter = AesAttributeConverter.class)
    @Column(nullable = false)
    private String name;

    @Comment("이메일(AES256 암호화)")
    @Setter
    @Convert(converter = AesAttributeConverter.class)
    @Column
    private String email;

    @Comment("연락처(AES256 암호화)")
    @Setter
    @Convert(converter = AesAttributeConverter.class)
    @Column(nullable = false)
    private String phoneNumber;

    @Comment("상태")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TeacherStatus status = TeacherStatus.WAIT;

    @Comment("역할")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private TeacherRole role = TeacherRole.TEACHER;

    @Comment("계정 상태")
    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private AuthenticationStatus authenticationStatus = AuthenticationStatus.INACTIVE;

    @Comment("비밀번호 틀린 횟수")
    @Setter
    @Column(nullable = false)
    @Builder.Default
    private Integer failedLoginAttempts = 0;

    @Comment("마지막 로그인 IP")
    @Setter
    @Column(length = 45)
    private String lastLoginIp;

    @Comment("마지막 로그인 일시")
    @Setter
    @Column
    private LocalDateTime lastLoginAt;

    @Comment("비고")
    @Setter
    @Column
    private String description;

    @Comment("소속 교육기관")
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Institute institute;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}