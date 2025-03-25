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
 * CttMissionQuiz 엔티티
 * 테이블 정보:
 * - 테이블 명: ctt_mission_quiz
 * - 테이블 코멘트: 독서퀴즈
 */
@Entity
@Table(name = "ctt_mission_quiz", indexes = {
        @Index(name = "idx_mission_quiz_1", columnList = "book_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CttMissionQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_quiz_no", nullable = false, updatable = false)
    private Integer missionQuizNo;

    @Column(name = "book_no", nullable = false)
    private Integer bookNo;

    @Column(name = "subjective_yn", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String subjectiveYn;

    @Column(name = "necessary_display_yn", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String necessaryDisplayYn;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @Column(name = "mission_quiz_type", nullable = false, length = 6, columnDefinition = "char(6)")
    @JdbcTypeCode(Types.CHAR)
    private String missionQuizType;

    @Lob
    @Column(name = "quiz_text", columnDefinition = "text")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String quizText;

    @Column(name = "quiz_question", length = 300, nullable = false)
    private String quizQuestion;

    @Column(name = "quiz_hint", length = 300)
    private String quizHint;

    @Column(name = "quiz_image_path", length = 200)
    private String quizImagePath;

    @Column(name = "quiz_answer", length = 200, nullable = false)
    private String quizAnswer;

    @Column(name = "show_choice_text", columnDefinition = "tinyint(1) default 1")
    private Boolean showChoiceText;

    @Column(name = "quiz_choice_1", length = 200)
    private String quizChoice1;

    @Column(name = "quiz_choice_1_image_path", length = 200)
    private String quizChoice1ImagePath;

    @Column(name = "quiz_choice_2", length = 200)
    private String quizChoice2;

    @Column(name = "quiz_choice_2_image_path", length = 200)
    private String quizChoice2ImagePath;

    @Column(name = "quiz_choice_3", length = 200)
    private String quizChoice3;

    @Column(name = "quiz_choice_3_image_path", length = 200)
    private String quizChoice3ImagePath;

    @Column(name = "quiz_choice_4", length = 200)
    private String quizChoice4;

    @Column(name = "quiz_choice_4_image_path", length = 200)
    private String quizChoice4ImagePath;

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

    @Lob
    @Column(name = "comment", columnDefinition = "text")
    @JdbcTypeCode(Types.LONGVARCHAR)
    private String comment;
}