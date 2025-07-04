package com.redutec.core.mapper;

import com.redutec.core.criteria.BookMbtiSurveyCriteria;
import com.redutec.core.dto.BookMbtiSurveyDto;
import com.redutec.core.entity.*;
import com.redutec.core.meta.BookMbtiResult;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
@Component
public class BookMbtiSurveyMapper {
    /**
     * CreateBookMbtiSurveyRequest DTO를 기반으로 BookMbtiSurvey 등록 엔티티를 생성합니다.
     *
     * @param createBookMbtiSurveyRequest 북BTI 설문 응답 등록 요청 객체
     * @param student 북BTI 설문에 답변한 학생 엔티티
     * @param bookBtiResult 북BTI 결과
     * @param findQuestionById 문항 ID로 BookMbtiQuestion 조회 함수
     * @param findQuestionChoiceById 선택지 ID로 BookMbtiQuestionChoice 조회 함수
     * @return 등록할 BookMbtiSurvey 엔티티
     */
    public BookMbtiSurvey createEntity(
            BookMbtiSurveyDto.CreateBookMbtiSurveyRequest createBookMbtiSurveyRequest,
            Student student,
            BookMbtiResult bookBtiResult,
            Function<Long, BookMbtiQuestion> findQuestionById,
            Function<Long, BookMbtiQuestionChoice> findQuestionChoiceById
    ) {
        // BookMbtiSurvey 엔티티 생성
        BookMbtiSurvey bookMbtiSurvey = BookMbtiSurvey.builder()
                .student(student)
                .result(bookBtiResult)
                .build();
        // BookMbtiSurveyAnswer 엔티티 생성 및 연관관계 설정
        for (BookMbtiSurveyDto.CreateBookMbtiSurveyAnswerRequest createAnswerRequest : createBookMbtiSurveyRequest.answers()) {
            BookMbtiQuestion bookMbtiQuestion = findQuestionById.apply(createAnswerRequest.bookMbtiQuestionId());
            BookMbtiQuestionChoice bookMbtiQuestionChoice = findQuestionChoiceById.apply(createAnswerRequest.answerChoiceId());
            BookMbtiSurveyAnswer bookMbtiSurveyAnswer = BookMbtiSurveyAnswer.builder()
                    .bookMbtiSurvey(bookMbtiSurvey)
                    .bookMbtiQuestion(bookMbtiQuestion)
                    .answerChoice(bookMbtiQuestionChoice)
                    .build();
            bookMbtiSurvey.getBookMbtiSurveyAnswers().add(bookMbtiSurveyAnswer);
        }
        return bookMbtiSurvey;
    }
    
    /**
     * 이 메서드는 현재 FindBookMbtiSurveyRequest 객체를 기반으로
     * BookMbtiSurveyCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 북BTI 설문 응답 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 BookMbtiSurveyCriteria 객체
     */
    public BookMbtiSurveyCriteria toCriteria(
            BookMbtiSurveyDto.FindBookMbtiSurveyRequest findBookMbtiSurveyRequest
    ) {
        return new BookMbtiSurveyCriteria(
                findBookMbtiSurveyRequest.bookMbtiSurveyIds(),
                findBookMbtiSurveyRequest.studentIds(),
                findBookMbtiSurveyRequest.bookMbtiResults()
        );
    }

    /**
     * BookMbtiSurvey 엔티티를 기반으로 응답용 BookMbtiSurveyResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param bookMbtiSurvey 변환할 BookMbtiSurvey 엔티티
     * @return BookMbtiSurvey 엔티티의 데이터를 담은 BookMbtiSurveyResponse DTO
     */
    public BookMbtiSurveyDto.BookMbtiSurveyResponse toResponseDto(
            @NonNull BookMbtiSurvey bookMbtiSurvey
    ) {
        List<BookMbtiSurveyDto.BookMbtiSurveyAnswerResponse> bookMbtiSurveyAnswerResponses = bookMbtiSurvey.getBookMbtiSurveyAnswers().stream()
                .map(answer -> new BookMbtiSurveyDto.BookMbtiSurveyAnswerResponse(
                        answer.getId(),
                        answer.getBookMbtiQuestion().getQuestion(),
                        answer.getAnswerChoice().getId(),
                        answer.getAnswerChoice().getLabel(),
                        answer.getAnswerChoice().getContent(),
                        answer.getAnswerChoice().getScore()
                ))
                .toList();
        return new BookMbtiSurveyDto.BookMbtiSurveyResponse(
                bookMbtiSurvey.getId(),
                bookMbtiSurvey.getStudent().getId(),
                bookMbtiSurvey.getStudent().getAccountId(),
                bookMbtiSurvey.getResult(),
                bookMbtiSurveyAnswerResponses,
                bookMbtiSurvey.getCreatedAt()
        );
    }
}