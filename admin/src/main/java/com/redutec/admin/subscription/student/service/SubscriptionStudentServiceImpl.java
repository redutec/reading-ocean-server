package com.redutec.admin.subscription.student.service;

import com.redutec.admin.student.service.StudentService;
import com.redutec.admin.subscription.plan.service.SubscriptionPlanService;
import com.redutec.core.dto.SubscriptionStudentDto;
import com.redutec.core.mapper.SubscriptionStudentMapper;
import com.redutec.core.entity.SubscriptionStudent;
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
    private final SubscriptionPlanService subscriptionPlanService;
    private final StudentService studentService;
    private final SubscriptionStudentMapper subscriptionStudentMapper;
    private final SubscriptionStudentRepository subscriptionStudentRepository;

    /**
     * 구독(학생) 등록
     * @param createSubscriptionStudentRequest 구독(학생) 등록 정보를 담은 DTO
     * @return 등록된 구독(학생) 정보
     */
    @Override
    @Transactional
    public SubscriptionStudentDto.SubscriptionStudentResponse create(
            SubscriptionStudentDto.CreateSubscriptionStudentRequest createSubscriptionStudentRequest
    ) {
        return subscriptionStudentMapper.toResponseDto(
                subscriptionStudentRepository.save(
                        subscriptionStudentMapper.toEntity(
                                createSubscriptionStudentRequest,
                                subscriptionPlanService.getSubscriptionPlan(createSubscriptionStudentRequest.subscriptionPlanId()),
                                studentService.getStudent(createSubscriptionStudentRequest.studentId())
                        )
                )
        );
    }

    /**
     * 조건에 맞는 구독(학생) 목록 조회
     * @param findSubscriptionStudentRequest 조회 조건을 담은 DTO
     * @return 조회된 구독(학생) 목록 및 페이징 정보
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
     * 특정 구독(학생) 조회
     * @param subscriptionStudentId 구독(학생) 고유번호
     * @return 특정 구독(학생) 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public SubscriptionStudentDto.SubscriptionStudentResponse findById(Long subscriptionStudentId) {
        return subscriptionStudentMapper.toResponseDto(getSubscriptionStudent(subscriptionStudentId));
    }

    /**
     * 특정 구독(학생) 엔티티 조회
     * @param subscriptionStudentId 구독(학생) 고유번호
     * @return 특정 구독(학생) 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public SubscriptionStudent getSubscriptionStudent(Long subscriptionStudentId) {
        return subscriptionStudentRepository.findById(subscriptionStudentId)
                .orElseThrow(() -> new EntityNotFoundException("구독(학생)를 찾을 수 없습니다. id = " + subscriptionStudentId));
    }

    /**
     * 특정 구독(학생) 수정
     * @param subscriptionStudentId 수정할 구독(학생)의 ID
     * @param updateSubscriptionStudentRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Long subscriptionStudentId,
            SubscriptionStudentDto.UpdateSubscriptionStudentRequest updateSubscriptionStudentRequest
    ) {
        // 수정할 구독(학생) 엔티티 조회
        SubscriptionStudent subscriptionStudent = getSubscriptionStudent(subscriptionStudentId);
        // UPDATE 도메인 메서드로 변환
        subscriptionStudent.updateSubscriptionStudent(
                Optional.ofNullable(updateSubscriptionStudentRequest.subscriptionPlanId())
                        .map(subscriptionPlanService::getSubscriptionPlan)
                        .orElse(null),
                updateSubscriptionStudentRequest.startedAt(),
                updateSubscriptionStudentRequest.endedAt(),
                updateSubscriptionStudentRequest.nextPaymentAt(),
                Optional.ofNullable(updateSubscriptionStudentRequest.studentId())
                        .map(studentService::getStudent)
                        .orElse(null)
        );
        // 구독(학생) 엔티티 UPDATE
        subscriptionStudentRepository.save(subscriptionStudent);
    }

    /**
     * 특정 구독(학생) 삭제
     * @param subscriptionStudentId 삭제할 구독(학생)의 ID
     */
    @Override
    @Transactional
    public void delete(Long subscriptionStudentId) {
        subscriptionStudentRepository.delete(getSubscriptionStudent(subscriptionStudentId));
    }
}