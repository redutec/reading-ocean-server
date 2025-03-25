package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;

/**
 * CmtConfigurationDiagnosisQuestion 엔티티
 * 테이블 정보:
 * - 테이블 명: cmt_configuration_diagnosis_question
 * - 테이블 코멘트: 독서진단 구성- 학년별 문항구성 (문항수, 문항별 배점, 진단레벨정의)
 */
@Entity
@Table(name = "cmt_configuration_diagnosis_question")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CmtConfigurationDiagnosisQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_configuration_no", nullable = false, updatable = false)
    private Integer diagnosisConfigurationNo;

    @Column(name = "school_grade", length = 15)
    private String schoolGrade;

    @Column(name = "diagnosis_category_value", length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String diagnosisCategoryValue;

    @Column(name = "diagnosis_subcategory_value", length = 6, nullable = false, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String diagnosisSubcategoryValue;

    @Column(name = "diagnosis_question_count", nullable = false, columnDefinition = "SMALLINT DEFAULT 0")
    private Short diagnosisQuestionCount;

    @Column(name = "diagnosis_question_point", nullable = false, columnDefinition = "SMALLINT DEFAULT 0")
    private Short diagnosisQuestionPoint;

    @Column(name = "diagnosis_right_answer_count", nullable = false, columnDefinition = "SMALLINT DEFAULT 0")
    private Short diagnosisRightAnswerCount;

    @Column(name = "diagnosis_right_answer_count2")
    private Short diagnosisRightAnswerCount2;

    @Column(name = "diagonsis_level_type", length = 45)
    private String diagonsisLevelType;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30, nullable = false)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}