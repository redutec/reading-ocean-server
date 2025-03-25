package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * EducationOfficeDiagnosisStudentResult 엔티티
 * 테이블 정보:
 * - 테이블 명: education_office_diagnosis_student_result
 * - 테이블 코멘트: 교육청 진단 학생 결과
 */
@Entity
@Table(name = "education_office_diagnosis_student_result")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class EducationOfficeDiagnosisStudentResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_office_diagnosis_student_result_no", nullable = false, updatable = false)
    private Integer educationOfficeDiagnosisStudentResultNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_office_diagnosis_student_no")
    private EducationOfficeDiagnosisStudent educationOfficeDiagnosisStudent;

    @Column(name = "raq", nullable = false)
    private Integer raq;

    @Column(name = "reading_level", length = 10)
    private String readingLevel;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_id", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}