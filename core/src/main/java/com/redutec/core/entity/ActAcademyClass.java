package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * ActAcademyClass 엔티티
 * 테이블 정보:
 * - 테이블 명: act_academy_class
 * - 테이블 코멘트: 학원 학급
 */
@Entity
@Table(name = "act_academy_class")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ActAcademyClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_no", nullable = false, updatable = false)
    private Integer classNo;

    @Column(name = "academy_no", nullable = false)
    private Integer academyNo;

    @Column(name = "class_name", length = 30, nullable = false)
    private String className;

    @Column(name = "teacher_account_no")
    private Integer teacherAccountNo;

    @Column(name = "teacher_enable_yn", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private Character teacherEnableYn;

    @Column(name = "use_yn", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y'")
    private Character useYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}