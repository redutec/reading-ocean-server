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
 * RdtReadingDiagnosisCommentDetail 엔티티
 * 테이블 정보:
 * - 테이블 명: rdt_reading_diagnosis_comment_detail
 * - 테이블 코멘트: 독서 진단 코멘트 상세
 */
@Entity
@Table(name = "rdt_reading_diagnosis_comment_detail")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class RdtReadingDiagnosisCommentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no", nullable = false, updatable = false)
    private Integer commentNo;

    @Column(name = "grade_group_code", length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String gradeGroupCode;

    @Column(name = "diagnosis_level_type", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String diagnosisLevelType;

    @Column(name = "diagnosis_category_value", length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String diagnosisCategoryValue;

    @Column(name = "diagnosis_subcategory_value", length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String diagnosisSubcategoryValue;

    @Lob
    @Column(name = "diagnosis_comment", nullable = false, columnDefinition = "text")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String diagnosisComment;

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