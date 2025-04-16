package com.redutec.core.entity;

import com.redutec.core.meta.ReadingLevel;
import com.redutec.core.meta.SchoolGrade;
import com.redutec.core.meta.StudentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
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

    @Comment("계정 아이디")
    @Column(length = 20, nullable = false)
    private String accountId;

    @Comment("비밀번호")
    @Column(nullable = false)
    private String password;

    @Comment("학생명(Base64 암호화)")
    @Column(nullable = false)
    private String name;

    @Comment("연락처(Base64 암호화)")
    @Column
    private String phoneNumber;

    @Comment("이메일(Base64 암호화)")
    @Column
    private String email;

    @Comment("생년월일")
    @Column(length = 8)
    @Pattern(regexp = "^[0-9]{8}$", message = "생년월일은 8자리의 숫자로만 구성되어야 합니다.")
    private LocalDate birthday;

    @Comment("상태")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private StudentStatus status = StudentStatus.WAIT;

    @Comment("독서레벨")
    @Column
    @Enumerated(EnumType.STRING)
    private ReadingLevel readingLevel;

    @Comment("학년")
    @Column
    @Enumerated(EnumType.STRING)
    private SchoolGrade schoolGrade;

    @Comment("실제 RAQ")
    @Column
    private Integer raq;

    @Comment("도서 포인트")
    @Column
    @PositiveOrZero
    private Integer bookPoints;

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
    private InstituteClass instituteClass;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public void updateStudent(
    ) {
    }
}