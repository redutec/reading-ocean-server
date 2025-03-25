package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * EducationOfficeDiagnosisStudentComment 엔티티
 * 테이블 정보:
 * - 테이블 명: education_office_diagnosis_student_comment
 * - 테이블 코멘트: 교육청 진단 학생 해설
 */
@Entity
@Table(name = "education_office_diagnosis_student_comment")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class EducationOfficeDiagnosisStudentComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_office_diagnosis_student_comment_no", nullable = false, updatable = false)
    private Integer educationOfficeDiagnosisStudentCommentNo;

    @Column(name = "education_office_diagnosis_student_no")
    private Integer educationOfficeDiagnosisStudentNo;

    @Column(name = "comment_no")
    private Integer commentNo;

    @Column(name = "diagnosis_level_type", length = 30)
    private String diagnosisLevelType;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_id", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}