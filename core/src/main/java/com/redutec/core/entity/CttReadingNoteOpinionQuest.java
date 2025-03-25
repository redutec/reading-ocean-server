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
 * CttReadingNoteOpinionQuest 엔티티
 * 테이블 정보:
 * - 테이블 명: ctt_reading_note_opinion_quest
 * - 테이블 코멘트: 독서노트-생각해보기
 */
@Entity
@Table(name = "ctt_reading_note_opinion_quest", indexes = {
        @Index(name = "idx_reading_note_opinion_quest_1", columnList = "book_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CttReadingNoteOpinionQuest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opinion_quest_no", nullable = false, updatable = false)
    private Integer opinionQuestNo;

    @Column(name = "book_no", nullable = false)
    private Integer bookNo;

    @Column(name = "necessary_display_yn", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String necessaryDisplayYn;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @Lob
    @Column(name = "quest_content", nullable = false, columnDefinition = "text")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String questContent;

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