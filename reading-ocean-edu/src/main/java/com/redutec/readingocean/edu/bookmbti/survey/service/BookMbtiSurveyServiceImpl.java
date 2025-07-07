package com.redutec.readingocean.edu.bookmbti.survey.service;

import com.redutec.core.dto.BookMbtiSurveyDto;
import com.redutec.core.entity.BookMbtiQuestion;
import com.redutec.core.entity.BookMbtiQuestionChoice;
import com.redutec.core.entity.BookMbtiSurvey;
import com.redutec.core.entity.Student;
import com.redutec.core.mapper.BookMbtiSurveyMapper;
import com.redutec.core.repository.BookMbtiQuestionChoiceRepository;
import com.redutec.core.repository.BookMbtiQuestionRepository;
import com.redutec.core.repository.BookMbtiSurveyRepository;
import com.redutec.core.repository.StudentRepository;
import com.redutec.readingocean.edu.authentication.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class BookMbtiSurveyServiceImpl implements BookMbtiSurveyService {
    private final BookMbtiSurveyRepository bookMbtiSurveyRepository;
    private final BookMbtiQuestionRepository bookMbtiQuestionRepository;
    private final BookMbtiQuestionChoiceRepository bookMbtiQuestionChoiceRepository;
    private final BookMbtiSurveyMapper bookMbtiSurveyMapper;
    private final AuthenticationService authenticationService;
    private final StudentRepository studentRepository;

    /**
     * 현재 로그인한 학생의 북BTI 설문 응답 등록
     *
     * @param createBookMbtiSurveyRequest 북BTI 설문 응답 등록 정보를 담은 DTO
     * @return 등록된 북BTI 설문 응답 정보
     */
    @Override
    public BookMbtiSurveyDto.BookMbtiSurveyResponse create(
            BookMbtiSurveyDto.CreateBookMbtiSurveyRequest createBookMbtiSurveyRequest
    ) {
        // 현재 로그인한 학생 가져오기
        Student student = studentRepository.findById(authenticationService.getAuthenticatedStudent().studentId())
                .orElseThrow(() -> new EntityNotFoundException("학생이 존재하지 않습니다. studentId: " + authenticationService.getAuthenticatedStudent().studentId()));
        // 빈 설문 엔티티 생성
        BookMbtiSurvey bookMbtiSurvey = BookMbtiSurvey.builder()
                .student(student)
                .build();
        // 답안 DTO 순회하며 엔티티에 추가
        for (BookMbtiSurveyDto.CreateBookMbtiSurveyAnswerRequest answerRequest : createBookMbtiSurveyRequest.answers()) {
            BookMbtiQuestion bookMbtiQuestion = bookMbtiQuestionRepository.findById(answerRequest.bookMbtiQuestionId())
                    .orElseThrow(() -> new EntityNotFoundException("설문이 존재하지 않습니다. bookMbtiQuestionId: " + answerRequest.bookMbtiQuestionId()));
            BookMbtiQuestionChoice answerChoice = bookMbtiQuestionChoiceRepository.findById(answerRequest.answerChoiceId())
                    .orElseThrow(() -> new EntityNotFoundException("선택지가 존재하지 않습니다. bookMbtiQuestionChoiceId: " + answerRequest.answerChoiceId()));
            bookMbtiSurvey.addAnswer(bookMbtiQuestion, answerChoice);
        }
        // 결과 계산
        bookMbtiSurvey.calculateResult();
        // 저장 및 DTO 변환
        BookMbtiSurvey savedBookMbtiSurvey = bookMbtiSurveyRepository.save(bookMbtiSurvey);
        return bookMbtiSurveyMapper.toResponseDto(savedBookMbtiSurvey);
    }

    /**
     * 현재 로그인한 학생의 가장 최근 설문 응답 조회
     *
     * @return 현재 로그인한 학생의 가장 최근 설문 응답 목록 및 페이징 정보
     */
    @Override
    public BookMbtiSurveyDto.BookMbtiSurveyResponse find() {
        // 현재 로그인한 학생 가져오기
        Student student = studentRepository.findById(authenticationService.getAuthenticatedStudent().studentId())
                .orElseThrow(() -> new EntityNotFoundException("학생이 존재하지 않습니다. studentId: " + authenticationService.getAuthenticatedStudent().studentId()));
        // 로그인한 학생의 가장 최근 설문 응답 조회
        BookMbtiSurvey bookMbtiSurvey = bookMbtiSurveyRepository.findFirstByStudentOrderByCreatedAtDesc(student)
                .orElseThrow(() -> new EntityNotFoundException("북BTI 설문 응답이 없습니다. studentId: " + authenticationService.getAuthenticatedStudent().studentId()));
        return bookMbtiSurveyMapper.toResponseDto(bookMbtiSurvey);
    }

}