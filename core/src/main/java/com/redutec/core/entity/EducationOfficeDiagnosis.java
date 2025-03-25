package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * EducationOfficeDiagnosis 엔티티
 * 테이블 정보:
 * - 테이블 명: education_office_diagnosis
 * - 테이블 코멘트: 교육청 진단 학교
 */
@Entity
@Table(name = "education_office_diagnosis")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class EducationOfficeDiagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_office_diagnosis_no", nullable = false, updatable = false)
    private Integer educationOfficeDiagnosisNo;

    @Column(name = "school_grade", length = 20, nullable = false)
    private String schoolGrade;

    @Column(name = "school_name", length = 255, nullable = false)
    private String schoolName;

    @Column(name = "file_path", length = 255, nullable = false)
    private String filePath;

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