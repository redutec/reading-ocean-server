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
 * RdtReadingDiagnosisPaper 엔티티
 * 테이블 정보:
 * - 테이블 명: rdt_reading_diagnosis_paper
 * - 테이블 코멘트: 독서능력 진단 지
 */
@Entity
@Table(name = "rdt_reading_diagnosis_paper")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class RdtReadingDiagnosisPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnosis_paper_no", nullable = false, updatable = false)
    private Integer diagnosisPaperNo;

    @Column(name = "school_grade", length = 15)
    private String schoolGrade;

    @Column(name = "diagnosis_paper_type", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String diagnosisPaperType;

    @Column(name = "diagnosis_category_value", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String diagnosisCategoryValue;

    @Column(name = "diagnosis_subcategory_value", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String diagnosisSubcategoryValue;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @Column(name = "diagnosis_question_text", length = 1500)
    private String diagnosisQuestionText;

    @Column(name = "diagnosis_question", length = 1500)
    private String diagnosisQuestion;

    @Column(name = "diagnosis_answer", nullable = false)
    private Byte diagnosisAnswer;

    @Column(name = "diagnosis_choice_1", length = 300)
    private String diagnosisChoice1;

    @Column(name = "diagnosis_choice_2", length = 300)
    private String diagnosisChoice2;

    @Column(name = "diagnosis_choice_3", length = 300)
    private String diagnosisChoice3;

    @Column(name = "diagnosis_choice_4", length = 300)
    private String diagnosisChoice4;

    @Column(name = "diagnosis_choice_5", length = 300)
    private String diagnosisChoice5;

    @Column(name = "diagnosis_question_description", length = 1500)
    private String diagnosisQuestionDescription;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30, nullable = false)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}