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
 * RatReadingNoteDetail 엔티티
 * 테이블 정보:
 * - 테이블 명: rat_reading_note_detail
 * - 테이블 코멘트: 독서노트 상세
 */
@Entity
@Table(name = "rat_reading_note_detail", indexes = {
        @Index(name = "idx_reading_note_detail_1", columnList = "reading_note_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class RatReadingNoteDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_note_detail_no", nullable = false, updatable = false)
    private Integer readingNoteDetailNo;

    @Column(name = "reading_note_no", nullable = false)
    private Integer readingNoteNo;

    @Column(name = "opinion_quest_no")
    private Integer opinionQuestNo;

    @Column(name = "bookreport_title", length = 100)
    private String bookreportTitle;

    @Lob
    @Column(name = "student_opinion", columnDefinition = "text")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String studentOpinion;

    @Column(name = "teacher_account_no")
    private Integer teacherAccountNo;

    @Lob
    @Column(name = "teacher_comment", columnDefinition = "text")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String teacherComment;

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