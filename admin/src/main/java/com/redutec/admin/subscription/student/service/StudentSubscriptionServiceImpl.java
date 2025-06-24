package com.redutec.admin.subscription.student.service;

import com.redutec.core.dto.StudentSubscriptionDto;
import com.redutec.core.entity.Student;
import com.redutec.core.entity.SubscriptionPlan;
import com.redutec.core.entity.StudentSubscription;
import com.redutec.core.mapper.StudentSubscriptionMapper;
import com.redutec.core.repository.StudentRepository;
import com.redutec.core.repository.SubscriptionPlanRepository;
import com.redutec.core.repository.StudentSubscriptionRepository;
import com.redutec.core.specification.StudentSubscriptionSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class StudentSubscriptionServiceImpl implements StudentSubscriptionService {
    private final StudentSubscriptionMapper studentSubscriptionMapper;
    private final StudentSubscriptionRepository studentSubscriptionRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final StudentRepository studentRepository;

    /**
     * 구독정보(학생) 등록
     * @param createStudentSubscriptionRequest 구독정보(학생) 등록 정보를 담은 DTO
     * @return 등록된 구독정보(학생) 정보
     */
    @Override
    @Transactional
    public StudentSubscriptionDto.StudentSubscriptionResponse create(
            StudentSubscriptionDto.CreateStudentSubscriptionRequest createStudentSubscriptionRequest
    ) {
        // 등록 요청 객체에 구독상품 고유번호가 존재한다면 구독상품 엔티티 조회
        SubscriptionPlan subscriptionPlan = Optional.ofNullable(createStudentSubscriptionRequest.subscriptionPlanId())
                .flatMap(subscriptionPlanRepository::findById)
                .orElse(null);
        // 등록 요청 객체에 구독상품을 구독할 학생의 고유번호가 존재한다면 학생의 엔티티 조회
        Student student = Optional.ofNullable(createStudentSubscriptionRequest.studentId())
                .flatMap(studentRepository::findById)
                .orElse(null);
        // 구독정보(학생) 등록
        return studentSubscriptionMapper.toResponseDto(
                studentSubscriptionRepository.save(
                        studentSubscriptionMapper.createEntity(
                                createStudentSubscriptionRequest,
                                subscriptionPlan,
                                student
                        )
                )
        );
    }

    /**
     * 조건에 맞는 구독정보(학생) 목록 조회
     * @param findStudentSubscriptionRequest 조회 조건을 담은 DTO
     * @return 조회된 구독정보(학생) 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public StudentSubscriptionDto.StudentSubscriptionPageResponse find(
            StudentSubscriptionDto.FindStudentSubscriptionRequest findStudentSubscriptionRequest
    ) {
        return studentSubscriptionMapper.toPageResponseDto(studentSubscriptionRepository.findAll(
                StudentSubscriptionSpecification.findWith(studentSubscriptionMapper.toCriteria(findStudentSubscriptionRequest)),
                (findStudentSubscriptionRequest.page() != null && findStudentSubscriptionRequest.size() != null)
                        ? PageRequest.of(findStudentSubscriptionRequest.page(), findStudentSubscriptionRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 구독정보(학생) 조회
     * @param studentSubscriptionId 구독정보(학생) 고유번호
     * @return 특정 구독정보(학생) 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public StudentSubscriptionDto.StudentSubscriptionResponse findById(Long studentSubscriptionId) {
        return studentSubscriptionMapper.toResponseDto(getStudentSubscription(studentSubscriptionId));
    }

    /**
     * 특정 구독정보(학생) 수정
     * @param studentSubscriptionId 수정할 구독정보(학생)의 ID
     * @param updateStudentSubscriptionRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Long studentSubscriptionId,
            StudentSubscriptionDto.UpdateStudentSubscriptionRequest updateStudentSubscriptionRequest
    ) {
        // 수정할 구독정보(학생) 엔티티 조회
        var subscriptionStudent = getStudentSubscription(studentSubscriptionId);
        // 수정 요청 객체에 구독상품 고유번호가 있다면 구독상품 엔티티 조회
        SubscriptionPlan subscriptionPlan = Optional.ofNullable(updateStudentSubscriptionRequest.subscriptionPlanId())
                .flatMap(subscriptionPlanRepository::findById)
                .orElseGet(subscriptionStudent::getSubscriptionPlan);
        // 수정 요청 객체에 구독상품을 구독할 학생 고유번호가 있다면 학생 엔티티 조회
        Student student = Optional.ofNullable(updateStudentSubscriptionRequest.studentId())
                .flatMap(studentRepository::findById)
                .orElseGet(subscriptionStudent::getStudent);
        // 구독정보(학생) 수정
        studentSubscriptionMapper.updateEntity(
                subscriptionStudent,
                updateStudentSubscriptionRequest,
                subscriptionPlan,
                student
        );
    }

    /**
     * 특정 구독정보(학생) 삭제
     * @param studentSubscriptionId 삭제할 구독정보(학생)의 ID
     */
    @Override
    @Transactional
    public void delete(Long studentSubscriptionId) {
        studentSubscriptionRepository.delete(getStudentSubscription(studentSubscriptionId));
    }

    /**
     * 특정 구독정보(학생) 엔티티 조회
     * @param studentSubscriptionId 구독정보(학생) 고유번호
     * @return 특정 구독정보(학생) 엔티티 객체
     */
    @Transactional(readOnly = true)
    public StudentSubscription getStudentSubscription(Long studentSubscriptionId) {
        return studentSubscriptionRepository.findById(studentSubscriptionId)
                .orElseThrow(() -> new EntityNotFoundException("구독정보(학생)을 찾을 수 없습니다. studentSubscriptionId: " + studentSubscriptionId));
    }
}