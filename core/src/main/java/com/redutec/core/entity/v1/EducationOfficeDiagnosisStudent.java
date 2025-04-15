package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * EducationOfficeDiagnosisStudent 엔티티
 * 테이블 정보:
 * - 테이블 명: education_office_diagnosis_student
 * - 테이블 코멘트: 교육청 진단 학생
 */
@Entity
@Table(name = "education_office_diagnosis_student")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class EducationOfficeDiagnosisStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_office_diagnosis_student_no", nullable = false, updatable = false)
    private Integer educationOfficeDiagnosisStudentNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_office_diagnosis_no")
    private EducationOfficeDiagnosis educationOfficeDiagnosis;

    @Column(name = "student_no")
    private Integer studentNo;

    @Column(name = "student_name", length = 20)
    private String studentName;

    @Column(name = "school_grade", length = 20)
    private String schoolGrade;

    @Column(name = "school_name", length = 100)
    private String schoolName;

    @Column(name = "diagnosis_type", length = 10)
    private String diagnosisType;

    @Column(name = "diagnosis_date", length = 10)
    private String diagnosisDate;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_id", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}