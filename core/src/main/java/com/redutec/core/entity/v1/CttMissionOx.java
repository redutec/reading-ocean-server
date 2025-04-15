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
 * CttMissionOx 엔티티
 * 테이블 정보:
 * - 테이블 명: ctt_mission_ox
 * - 테이블 코멘트: OX퀴즈
 */
@Entity
@Table(name = "ctt_mission_ox", indexes = {
        @Index(name = "idx_mission_ox_1", columnList = "book_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CttMissionOx {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OX_quiz_no", nullable = false, updatable = false)
    private Integer oxQuizNo;

    @Column(name = "book_no", nullable = false)
    private Integer bookNo;

    @Column(name = "necessary_display_yn", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String necessaryDisplayYn;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @Column(name = "OX_quiz_question", length = 300, nullable = false)
    private String oxQuizQuestion;

    @Column(name = "OX_quiz_answer", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String oxQuizAnswer;

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

    @Lob
    @Column(name = "comment", columnDefinition = "text")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String comment;
}