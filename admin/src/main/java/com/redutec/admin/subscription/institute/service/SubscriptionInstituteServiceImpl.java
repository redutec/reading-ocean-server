package com.redutec.admin.subscription.institute.service;

import com.redutec.admin.institute.service.InstituteService;
import com.redutec.admin.subscription.plan.service.SubscriptionPlanService;
import com.redutec.core.dto.SubscriptionInstituteDto;
import com.redutec.core.entity.SubscriptionInstitute;
import com.redutec.core.mapper.SubscriptionInstituteMapper;
import com.redutec.core.repository.SubscriptionInstituteRepository;
import com.redutec.core.specification.SubscriptionInstituteSpecification;
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
public class SubscriptionInstituteServiceImpl implements SubscriptionInstituteService {
    private final SubscriptionPlanService subscriptionPlanService;
    private final InstituteService instituteService;
    private final SubscriptionInstituteMapper subscriptionInstituteMapper;
    private final SubscriptionInstituteRepository subscriptionInstituteRepository;

    /**
     * 구독(교육기관) 등록
     * @param createSubscriptionInstituteRequest 구독(교육기관) 등록 정보를 담은 DTO
     * @return 등록된 구독(교육기관) 정보
     */
    @Override
    @Transactional
    public SubscriptionInstituteDto.SubscriptionInstituteResponse create(
            SubscriptionInstituteDto.CreateSubscriptionInstituteRequest createSubscriptionInstituteRequest
    ) {
        return subscriptionInstituteMapper.toResponseDto(
                subscriptionInstituteRepository.save(
                        subscriptionInstituteMapper.toCreateEntity(
                                createSubscriptionInstituteRequest,
                                subscriptionPlanService.getSubscriptionPlan(createSubscriptionInstituteRequest.subscriptionPlanId()),
                                instituteService.getInstitute(createSubscriptionInstituteRequest.instituteId())
                        )
                )
        );
    }

    /**
     * 조건에 맞는 구독(교육기관) 목록 조회
     * @param findSubscriptionInstituteRequest 조회 조건을 담은 DTO
     * @return 조회된 구독(교육기관) 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public SubscriptionInstituteDto.SubscriptionInstitutePageResponse find(
            SubscriptionInstituteDto.FindSubscriptionInstituteRequest findSubscriptionInstituteRequest
    ) {
        return subscriptionInstituteMapper.toPageResponseDto(subscriptionInstituteRepository.findAll(
                SubscriptionInstituteSpecification.findWith(subscriptionInstituteMapper.toCriteria(findSubscriptionInstituteRequest)),
                (findSubscriptionInstituteRequest.page() != null && findSubscriptionInstituteRequest.size() != null)
                        ? PageRequest.of(findSubscriptionInstituteRequest.page(), findSubscriptionInstituteRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 구독(교육기관) 조회
     * @param subscriptionInstituteId 구독(교육기관) 고유번호
     * @return 특정 구독(교육기관) 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public SubscriptionInstituteDto.SubscriptionInstituteResponse findById(Long subscriptionInstituteId) {
        return subscriptionInstituteMapper.toResponseDto(getSubscriptionInstitute(subscriptionInstituteId));
    }

    /**
     * 특정 구독(교육기관) 엔티티 조회
     * @param subscriptionInstituteId 구독(교육기관) 고유번호
     * @return 특정 구독(교육기관) 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public SubscriptionInstitute getSubscriptionInstitute(Long subscriptionInstituteId) {
        return subscriptionInstituteRepository.findById(subscriptionInstituteId)
                .orElseThrow(() -> new EntityNotFoundException("구독(교육기관)를 찾을 수 없습니다. subscriptionInstituteId = " + subscriptionInstituteId));
    }

    /**
     * 특정 구독(교육기관) 수정
     * @param subscriptionInstituteId 수정할 구독(교육기관)의 ID
     * @param updateSubscriptionInstituteRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Long subscriptionInstituteId,
            SubscriptionInstituteDto.UpdateSubscriptionInstituteRequest updateSubscriptionInstituteRequest
    ) {
        // 수정할 구독(교육기관) 엔티티 조회
        SubscriptionInstitute subscriptionInstitute = getSubscriptionInstitute(subscriptionInstituteId);
        subscriptionInstituteRepository.save(subscriptionInstituteMapper.toUpdateEntity(
                subscriptionInstitute,
                updateSubscriptionInstituteRequest,
                Optional.ofNullable(updateSubscriptionInstituteRequest.subscriptionPlanId())
                        .map(subscriptionPlanService::getSubscriptionPlan)
                        .orElse(null),
                Optional.ofNullable(updateSubscriptionInstituteRequest.instituteId())
                        .map(instituteService::getInstitute)
                        .orElse(null)
        ));
    }

    /**
     * 특정 구독(교육기관) 삭제
     * @param subscriptionInstituteId 삭제할 구독(교육기관)의 ID
     */
    @Override
    @Transactional
    public void delete(Long subscriptionInstituteId) {
        subscriptionInstituteRepository.delete(getSubscriptionInstitute(subscriptionInstituteId));
    }
}