package com.redutec.core.mapper;

import com.redutec.core.criteria.BookMbtiQuestionCriteria;
import com.redutec.core.dto.BookMbtiQuestionDto;
import com.redutec.core.entity.BookMbtiQuestion;
import com.redutec.core.entity.BookMbtiQuestionChoice;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class BookMbtiQuestionMapper {
    /**
     * CreateBookMbtiQuestionRequest DTO를 기반으로 BookMbtiQuestion 등록 엔티티를 생성합니다.
     *
     * @param createBookMbtiQuestionRequest 북BTI 설문 문항 등록에 필요한 데이터를 담은 DTO
     * @return 등록할 BookMbtiQuestion 엔티티
     */
    public BookMbtiQuestion createEntity(
            BookMbtiQuestionDto.CreateBookMbtiQuestionRequest createBookMbtiQuestionRequest
    ) {
        // BookMbtiQuestion 엔티티 생성
        BookMbtiQuestion bookMbtiQuestion = BookMbtiQuestion.builder()
                .type(createBookMbtiQuestionRequest.type())
                .question(StringUtils.stripToNull(createBookMbtiQuestionRequest.question()))
                .build();
        // BookMbtiQuestionChoice 엔티티 생성 및 연관관계 설정
        for (BookMbtiQuestionDto.CreateBookMbtiQuestionChoiceRequest createChoiceRequest : createBookMbtiQuestionRequest.choices()) {
            BookMbtiQuestionChoice bookMbtiQuestionChoice = BookMbtiQuestionChoice.builder()
                    .bookMbtiQuestion(bookMbtiQuestion)
                    .label(createChoiceRequest.label())
                    .content(StringUtils.stripToNull(createChoiceRequest.content()))
                    .score(createChoiceRequest.score())
                    .build();
            bookMbtiQuestion.getChoices().add(bookMbtiQuestionChoice);
        }
        // 등록할 BookMbtiQuestion 엔티티 리턴
        return bookMbtiQuestion;
    }

    /**
     * UpdateBookMbtiQuestionRequest DTO를 기반으로 BookMbtiQuestion 엔티티를 수정합니다.
     *
     * @param bookMbtiQuestion 수정할 BookMbtiQuestion 엔티티
     * @param updateBookMbtiQuestionRequest 북BTI 설문 문항 수정에 필요한 데이터를 담은 DTO
     */
    public void updateEntity(
            BookMbtiQuestion bookMbtiQuestion,
            BookMbtiQuestionDto.UpdateBookMbtiQuestionRequest updateBookMbtiQuestionRequest
    ) {
        Optional.ofNullable(updateBookMbtiQuestionRequest.type())
                .ifPresent(bookMbtiQuestion::setType);
        Optional.ofNullable(StringUtils.stripToNull(updateBookMbtiQuestionRequest.question()))
                .ifPresent(bookMbtiQuestion::setQuestion);
        Optional.ofNullable(updateBookMbtiQuestionRequest.choices())
                .ifPresent(choices -> {
                    // 요청 객체에 없는 기존 선택지 제거
                    var incomingChoiceIds = choices.stream()
                            .map(BookMbtiQuestionDto.UpdateBookMbtiQuestionChoiceRequest::choiceId)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toSet());
                    bookMbtiQuestion.getChoices().removeIf(choice ->
                            choice.getId() != null && !incomingChoiceIds.contains(choice.getId())
                    );
                    // 기존 설문 문항 선택지 수정: choiceId가 있는 DTO → 기존 엔티티 찾아 업데이트
                    choices.stream()
                            .filter(dto -> dto.choiceId() != null)
                            .forEach(dto -> bookMbtiQuestion.getChoices().stream()
                                    .filter(choice -> choice.getId().equals(dto.choiceId()))
                                    .findFirst()
                                    .ifPresentOrElse(
                                            choice -> {
                                                choice.setLabel(dto.label());
                                                choice.setContent(StringUtils.stripToNull(dto.content()));
                                                choice.setScore(dto.score());
                                            },
                                            () -> { throw new EntityNotFoundException("존재하지 않는 선택지입니다. choiceId: " + dto.choiceId()); }
                                    )
                            );

                    // 새로운 설문 문항 선택지 추가: choiceId가 없는 DTO → 새 엔티티 생성 후 추가
                    choices.stream()
                            .filter(dto -> dto.choiceId() == null)
                            .map(dto -> BookMbtiQuestionChoice.builder()
                                    .bookMbtiQuestion(bookMbtiQuestion)
                                    .label(dto.label())
                                    .content(StringUtils.stripToNull(dto.content()))
                                    .score(dto.score())
                                    .build()
                            )
                            .forEach(bookMbtiQuestion.getChoices()::add);
                });
    }
    
    /**
     * 이 메서드는 현재 FindBookMbtiQuestionRequest 객체를 기반으로
     * BookMbtiQuestionCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 북BTI 설문 문항 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 BookMbtiQuestionCriteria 객체
     */
    public BookMbtiQuestionCriteria toCriteria(
            BookMbtiQuestionDto.FindBookMbtiQuestionRequest findBookMbtiQuestionRequest
    ) {
        return new BookMbtiQuestionCriteria(
                findBookMbtiQuestionRequest.bookMbtiQuestionIds(),
                findBookMbtiQuestionRequest.types(),
                StringUtils.stripToNull(findBookMbtiQuestionRequest.question())
        );
    }

    /**
     * BookMbtiQuestion 엔티티를 기반으로 응답용 BookMbtiQuestionResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param bookMbtiQuestion 변환할 BookMbtiQuestion 엔티티
     * @return BookMbtiQuestion 엔티티의 데이터를 담은 BookMbtiQuestionResponse DTO
     */
    public BookMbtiQuestionDto.BookMbtiQuestionResponse toResponseDto(
            @NonNull BookMbtiQuestion bookMbtiQuestion
    ) {
        List<BookMbtiQuestionDto.BookMbtiQuestionChoiceResponse> bookMbtiQuestionChoiceResponses = bookMbtiQuestion.getChoices().stream()
                .map(choice -> new BookMbtiQuestionDto.BookMbtiQuestionChoiceResponse(
                        choice.getId(),
                        choice.getLabel(),
                        choice.getContent(),
                        choice.getScore()
                ))
                .toList();
        return new BookMbtiQuestionDto.BookMbtiQuestionResponse(
                bookMbtiQuestion.getId(),
                bookMbtiQuestion.getType(),
                bookMbtiQuestion.getQuestion(),
                bookMbtiQuestionChoiceResponses,
                bookMbtiQuestion.getCreatedAt(),
                bookMbtiQuestion.getUpdatedAt()
        );
    }
}