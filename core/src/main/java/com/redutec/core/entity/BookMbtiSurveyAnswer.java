package com.redutec.core.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Comment("북BTI 설문 응답 상세내역")
@DynamicUpdate
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class BookMbtiSurveyAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("북BTI 설문 응답 상세내역 고유번호")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_mbti_survey_id", nullable = false)
    @Setter
    @Comment("연결된 북BTI 설문 응답")
    private BookMbtiSurvey bookMbtiSurvey;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_mbti_question_id", nullable = false)
    @Setter
    @Comment("응답한 북BTI 설문 문항")
    private BookMbtiQuestion bookMbtiQuestion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_mbti_question_choice_id", nullable = false)
    @Setter
    @Comment("응답한 선택지")
    private BookMbtiQuestionChoice answerChoice;
}