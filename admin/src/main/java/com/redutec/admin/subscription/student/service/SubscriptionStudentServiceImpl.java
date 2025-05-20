package com.redutec.admin.subscription.student.service;

import com.redutec.core.dto.SubscriptionStudentDto;
import com.redutec.core.entity.Student;
import com.redutec.core.entity.SubscriptionPlan;
import com.redutec.core.entity.SubscriptionStudent;
import com.redutec.core.mapper.SubscriptionStudentMapper;
import com.redutec.core.repository.StudentRepository;
import com.redutec.core.repository.SubscriptionPlanRepository;
import com.redutec.core.repository.SubscriptionStudentRepository;
import com.redutec.core.specification.SubscriptionStudentSpecification;
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
public class SubscriptionStudentServiceImpl implements SubscriptionStudentService {
    private final SubscriptionStudentMapper subscriptionStudentMapper;
    private final SubscriptionStudentRepository subscriptionStudentRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final StudentRepository studentRepository;

    /**
     * 구독정보(학생) 등록
     * @param createSubscriptionStudentRequest 구독정보(학생) 등록 정보를 담은 DTO
     * @return 등록된 구독정보(학생) 정보
     */
    @Override
    @Transactional
    public SubscriptionStudentDto.SubscriptionStudentResponse create(
            SubscriptionStudentDto.CreateSubscriptionStudentRequest createSubscriptionStudentRequest
    ) {
        // 등록 요청 객체에 구독상품 고유번호가 존재한다면 구독상품 엔티티 조회
        SubscriptionPlan subscriptionPlan = Optional.ofNullable(createSubscriptionStudentRequest.subscriptionPlanId())
                .flatMap(subscriptionPlanRepository::findById)
                .orElse(null);
        // 등록 요청 객체에 구독상품을 구독할 학생의 고유번호가 존재한다면 학생의 엔티티 조회
        Student student = Optional.ofNullable(createSubscriptionStudentRequest.studentId())
                .flatMap(studentRepository::findById)
                .orElse(null);
        // 구독정보(학생) 등록
        return subscriptionStudentMapper.toResponseDto(
                subscriptionStudentRepository.save(
                        subscriptionStudentMapper.toCreateEntity(
                                createSubscriptionStudentRequest,
                                subscriptionPlan,
                                student
                        )
                )
        );
    }

    /**
     * 조건에 맞는 구독정보(학생) 목록 조회
     * @param findSubscriptionStudentRequest 조회 조건을 담은 DTO
     * @return 조회된 구독정보(학생) 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public SubscriptionStudentDto.SubscriptionStudentPageResponse find(
            SubscriptionStudentDto.FindSubscriptionStudentRequest findSubscriptionStudentRequest
    ) {
        return subscriptionStudentMapper.toPageResponseDto(subscriptionStudentRepository.findAll(
                SubscriptionStudentSpecification.findWith(subscriptionStudentMapper.toCriteria(findSubscriptionStudentRequest)),
                (findSubscriptionStudentRequest.page() != null && findSubscriptionStudentRequest.size() != null)
                        ? PageRequest.of(findSubscriptionStudentRequest.page(), findSubscriptionStudentRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 구독정보(학생) 조회
     * @param subscriptionStudentId 구독정보(학생) 고유번호
     * @return 특정 구독정보(학생) 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public SubscriptionStudentDto.SubscriptionStudentResponse findById(Long subscriptionStudentId) {
        return subscriptionStudentMapper.toResponseDto(getSubscriptionStudent(subscriptionStudentId));
    }

    /**
     * 특정 구독정보(학생) 수정
     * @param subscriptionStudentId 수정할 구독정보(학생)의 ID
     * @param updateSubscriptionStudentRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Long subscriptionStudentId,
            SubscriptionStudentDto.UpdateSubscriptionStudentRequest updateSubscriptionStudentRequest
    ) {
        // 수정 요청 객체에 구독상품 고유번호가 있다면 구독상품 엔티티 조회
        SubscriptionPlan subscriptionPlan = Optional.ofNullable(updateSubscriptionStudentRequest.subscriptionPlanId())
                .flatMap(subscriptionPlanRepository::findById)
                .orElse(null);
        // 수정 요청 객체에 구독상품을 구독할 학생 고유번호가 있다면 학생 엔티티 조회
        Student student = Optional.ofNullable(updateSubscriptionStudentRequest.studentId())
                .flatMap(studentRepository::findById)
                .orElse(null);
        // 구독정보(학생) 수정
        subscriptionStudentRepository.save(subscriptionStudentMapper.toUpdateEntity(
                getSubscriptionStudent(subscriptionStudentId),
                updateSubscriptionStudentRequest,
                subscriptionPlan,
                student
        ));
    }

    /**
     * 특정 구독정보(학생) 삭제
     * @param subscriptionStudentId 삭제할 구독정보(학생)의 ID
     */
    @Override
    @Transactional
    public void delete(Long subscriptionStudentId) {
        subscriptionStudentRepository.delete(getSubscriptionStudent(subscriptionStudentId));
    }

    /**
     * 특정 구독정보(학생) 엔티티 조회
     * @param subscriptionStudentId 구독정보(학생) 고유번호
     * @return 특정 구독정보(학생) 엔티티 객체
     */
    @Transactional(readOnly = true)
    public SubscriptionStudent getSubscriptionStudent(Long subscriptionStudentId) {
        return subscriptionStudentRepository.findById(subscriptionStudentId)
                .orElseThrow(() -> new EntityNotFoundException("구독정보(학생)을 찾을 수 없습니다. subscriptionStudentId = " + subscriptionStudentId));
    }
}