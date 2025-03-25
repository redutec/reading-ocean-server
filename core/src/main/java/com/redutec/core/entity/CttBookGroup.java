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
 * CttBookGroup 엔티티
 * 테이블 정보:
 * - 테이블 명: ctt_book_group
 * - 테이블 코멘트: 도서 그룹(추천도서/연계도서)
 */
@Entity
@Table(name = "ctt_book_group")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CttBookGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_group_no", nullable = false, updatable = false)
    private Integer bookGroupNo;

    @Column(name = "display_year_month", length = 10, nullable = false)
    private String displayYearMonth;

    @Column(name = "book_group_type", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String bookGroupType;

    @Column(name = "book_group_name", length = 30, nullable = false)
    private String bookGroupName;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @Column(name = "curriculum_linked_grade", length = 15)
    private String curriculumLinkedGrade;

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