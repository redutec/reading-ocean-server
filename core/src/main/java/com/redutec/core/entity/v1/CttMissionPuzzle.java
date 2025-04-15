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
 * CttMissionPuzzle 엔티티
 * 테이블 정보:
 * - 테이블 명: ctt_mission_puzzle
 * - 테이블 코멘트: 낱말퍼즐
 */
@Entity
@Table(name = "ctt_mission_puzzle", indexes = {
        @Index(name = "idx_mission_puzzle_1", columnList = "book_no")
})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CttMissionPuzzle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "puzzle_no", nullable = false, updatable = false)
    private Integer puzzleNo;

    @Column(name = "book_no", nullable = false)
    private Integer bookNo;

    @Column(name = "begin_width_position", nullable = false)
    private Byte beginWidthPosition;

    @Column(name = "begin_height_position", nullable = false)
    private Byte beginHeightPosition;

    @Column(name = "width_yn", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String widthYn;

    @Column(name = "use_yn", nullable = false, columnDefinition = "char(1)")
    @JdbcTypeCode(Types.CHAR)
    private String useYn;

    @Column(name = "word_count", nullable = false)
    private Byte wordCount;

    @Column(name = "puzzle_answer", length = 10, nullable = false)
    private String puzzleAnswer;

    @Column(name = "word_description", length = 200, nullable = false)
    private String wordDescription;

    @Column(name = "puzzle_choice", length = 200)
    private String puzzleChoice;

    @Column(name = "puzzle_word_question_no", length = 3, nullable = false)
    private String puzzleWordQuestionNo;

    @Column(name = "puzzle_word_image_path", length = 200)
    private String puzzleWordImagePath;

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