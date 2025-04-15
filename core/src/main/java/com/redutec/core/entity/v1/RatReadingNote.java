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
 * RatReadingNote 엔티티
 * 테이블 정보:
 * - 테이블 명: rat_reading_note
 * - 테이블 코멘트: 독서노트(생각해보기, 독후감- 제출완료시 "도서포인트 * 지급비율" 결과 값을 독서포인트로 지급 처리 & 독후활동 진행률 처리 - 트랜잭션 묶음)
 */
@Entity
@Table(name = "rat_reading_note", indexes = {
        @Index(name = "idx_reading_note_1", columnList = "book_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class RatReadingNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_note_no", nullable = false, updatable = false)
    private Integer readingNoteNo;

    @Column(name = "account_no", nullable = false)
    private Integer accountNo;

    @Column(name = "book_no", nullable = false)
    private Integer bookNo;

    @Column(name = "reading_mission_type", length = 20)
    private String readingMissionType;

    @Column(name = "on_reading_RAQ")
    private Short onReadingRAQ;

    @Column(name = "on_reading_level", length = 10)
    private String onReadingLevel;

    @Column(name = "on_reading_school_grade", length = 15)
    private String onReadingSchoolGrade;

    @Column(name = "on_reading_class_no")
    private Integer onReadingClassNo;

    @Column(name = "bookreport_reading_note_status", nullable = false)
    private Byte bookreportReadingNoteStatus;

    @Column(name = "charge_book_point_yn", nullable = false, columnDefinition = "char default 'N'")
    @JdbcTypeCode(Types.CHAR)
    private String chargeBookPointYn;

    @Column(name = "charge_book_point_amount", nullable = false, columnDefinition = "int default 0")
    private Integer chargeBookPointAmount;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT (now())")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_ID", length = 30)
    private String adminID;

    @Column(name = "description", length = 300)
    private String description;
}