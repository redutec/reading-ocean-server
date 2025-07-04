package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Comment("북BTI 설문 문항 선택지")
@DynamicUpdate
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class BookMbtiQuestionChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("북BTI 문항 선택지 고유번호")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_mbti_question_id", nullable = false)
    @Setter
    @Comment("이 선택지가 소속된 설문 문항")
    private BookMbtiQuestion bookMbtiQuestion;

    @Column(nullable = false)
    @Setter
    @Comment("선택지 레이블(예: 1, 2, 3, 4)")
    private Integer label;

    @Column(nullable = false)
    @Setter
    @Comment("선택지 내용(예: “친구랑 대화하는 게 편하다”)")
    private String content;

    @Column(nullable = false)
    @Setter
    @Comment("점수 또는 북BTI 지표 매핑 값")
    private Integer score;
}