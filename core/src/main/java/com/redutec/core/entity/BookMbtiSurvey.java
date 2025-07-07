package com.redutec.core.entity;

import com.redutec.core.meta.BookMbtiQuestionType;
import com.redutec.core.meta.BookMbtiResult;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Entity
@Comment("북BTI 설문 응답")
@DynamicUpdate
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class BookMbtiSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("북BTI 설문 응답 고유번호")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    @Setter
    @Comment("응답자 학생")
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    @Comment("북BTI 결과")
    private BookMbtiResult result;

    @OneToMany(mappedBy = "bookMbtiSurvey", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @Setter
    @Comment("북BTI 설문 응답 상세내역")
    private List<BookMbtiSurveyAnswer> bookMbtiSurveyAnswers = new ArrayList<>();

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Comment("설문 응답 완료 일시")
    private LocalDateTime createdAt;

    /**
     * 새 답안을 이 설문에 추가합니다.
     */
    public void addAnswer(BookMbtiQuestion bookMbtiQuestion, BookMbtiQuestionChoice answerChoice) {
        BookMbtiSurveyAnswer surveyAnswer = BookMbtiSurveyAnswer.builder()
                .bookMbtiSurvey(this)
                .bookMbtiQuestion(bookMbtiQuestion)
                .answerChoice(answerChoice)
                .build();
        this.bookMbtiSurveyAnswers.add(surveyAnswer);
    }

    /**
     * 모든 답안을 순회하며 유형별 점수를 합산하고,
     * 최종 MBTI 결과를 결정해 this.result 에 설정합니다.
     */
    public void calculateResult() {
        Map<BookMbtiQuestionType, Integer> scoreMap = new EnumMap<>(BookMbtiQuestionType.class);
        for (BookMbtiQuestionType questionType : BookMbtiQuestionType.values()) {
            scoreMap.put(questionType, 0);
        }
        for (BookMbtiSurveyAnswer surveyAnswer : bookMbtiSurveyAnswers) {
            BookMbtiQuestionType questionType = surveyAnswer.getBookMbtiQuestion().getType();
            int currentScore = scoreMap.get(questionType);
            int addedScore = surveyAnswer.getAnswerChoice().getScore();
            scoreMap.put(questionType, currentScore + addedScore);
        }
        String resultCode =
                (scoreMap.get(BookMbtiQuestionType.E_I) >= 0 ? "E" : "I")
                        + (scoreMap.get(BookMbtiQuestionType.S_N) >= 0 ? "S" : "N")
                        + (scoreMap.get(BookMbtiQuestionType.F_T) >= 0 ? "T" : "F")
                        + (scoreMap.get(BookMbtiQuestionType.J_P) >= 0 ? "J" : "P");
        this.result = BookMbtiResult.valueOf(resultCode);
    }
}