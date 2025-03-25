package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * EducationOfficeDiagnosisStudentAnswer 엔티티
 * 테이블 정보:
 * - 테이블 명: education_office_diagnosis_student_answer
 * - 테이블 코멘트: 교육청 진단 학생 답변
 */
@Entity
@Table(name = "education_office_diagnosis_student_answer")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class EducationOfficeDiagnosisStudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_office_diagnosis_student_answer_no", nullable = false, updatable = false)
    private Integer educationOfficeDiagnosisStudentAnswerNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "education_office_diagnosis_student_no")
    private EducationOfficeDiagnosisStudent educationOfficeDiagnosisStudent;

    @Column(name = "diagnosis_subcategory", length = 255)
    private String diagnosisSubcategory;

    @Column(name = "answer_count")
    private Integer answerCount;

    @Column(name = "question_count")
    private Integer questionCount;

    @Column(name = "answer_percent")
    private Integer answerPercent;

    @Column(name = "apply_score")
    private Integer applyScore;

    @Column(name = "grade_average_score")
    private Integer gradeAverageScore;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_id", length = 300)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}