package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * BookbtiQuestion 엔티티
 * 테이블 정보:
 * - 테이블 명: bookbti_question
 * - 테이블 코멘트: 북BTI 문항
 */
@Entity
@Table(name = "bookbti_question")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class BookbtiQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookbti_question_no", nullable = false, updatable = false)
    private Integer bookbtiQuestionNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookbti_no", nullable = false)
    private Bookbti bookbti;

    @Column(name = "type", length = 10, nullable = false)
    private String type;

    @Column(name = "content", length = 255, nullable = false)
    private String content;

    @Column(name = "use_yn", nullable = false, columnDefinition = "CHAR(1)")
    private Character useYn;

    @CreatedDate
    @Column(name = "register_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime registerDatetime;

    @LastModifiedDate
    @Column(name = "modify_datetime", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime modifyDatetime;

    @Column(name = "admin_id", length = 30)
    private String adminId;

    @Column(name = "description", length = 300)
    private String description;
}