package com.redutec.core.entity.v1;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Types;
import java.time.LocalDateTime;

/**
 * CttUnit 엔티티
 * 테이블 정보:
 * - 테이블 명: ctt_unit
 * - 테이블 코멘트: 연계 교과 단원
 */
@Entity
@Table(name = "ctt_unit")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CttUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "link_unit_no", nullable = false, updatable = false)
    private Integer linkUnitNo;

    @Column(name = "curriculum_linked_grade", length = 15)
    private String curriculumLinkedGrade;

    @Column(name = "link_course_value", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String linkCourseValue;

    @Column(name = "link_unit_name", length = 30, nullable = false)
    private String linkUnitName;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1) default 'Y'")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now()) ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", nullable = false, length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}