package com.redutec.admin.subscription.institute.service;

import com.redutec.core.dto.InstituteSubscriptionDto;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.InstituteSubscription;
import com.redutec.core.entity.SubscriptionPlan;
import com.redutec.core.mapper.InstituteSubscriptionMapper;
import com.redutec.core.repository.InstituteRepository;
import com.redutec.core.repository.InstituteSubscriptionRepository;
import com.redutec.core.repository.SubscriptionPlanRepository;
import com.redutec.core.specification.InstituteSubscriptionSpecification;
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
public class InstituteSubscriptionServiceImpl implements InstituteSubscriptionService {
    private final InstituteSubscriptionMapper subscriptionInstituteMapper;
    private final InstituteSubscriptionRepository instituteSubscriptionRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final InstituteRepository instituteRepository;

    /**
     * 구독정보(교육기관) 등록
     * @param createInstituteSubscriptionRequest 구독정보(교육기관) 등록 정보를 담은 DTO
     * @return 등록된 구독정보(교육기관) 정보
     */
    @Override
    @Transactional
    public InstituteSubscriptionDto.InstituteSubscriptionResponse create(
            InstituteSubscriptionDto.CreateInstituteSubscriptionRequest createInstituteSubscriptionRequest
    ) {
        // 등록 요청 객체에 구독상품 고유번호가 존재하면 구독상품 엔티티를 조회
        SubscriptionPlan subscriptionPlan = Optional.ofNullable(createInstituteSubscriptionRequest.subscriptionPlanId())
                .flatMap(subscriptionPlanRepository::findById)
                .orElse(null);
        // 구독하는 교육기관 고유번호가 존재하면 교육기관 엔티티를 조회
        Institute institute = Optional.ofNullable(createInstituteSubscriptionRequest.instituteId())
                .flatMap(instituteRepository::findById)
                .orElse(null);
        // 구독정보(교육기관) 등록
        return subscriptionInstituteMapper.toResponseDto(
                instituteSubscriptionRepository.save(
                        subscriptionInstituteMapper.createEntity(
                                createInstituteSubscriptionRequest,
                                subscriptionPlan,
                                institute
                        )
                )
        );
    }

    /**
     * 조건에 맞는 구독정보(교육기관) 목록 조회
     * @param findInstituteSubscriptionRequest 조회 조건을 담은 DTO
     * @return 조회된 구독정보(교육기관) 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public InstituteSubscriptionDto.InstituteSubscriptionPageResponse find(
            InstituteSubscriptionDto.FindInstituteSubscriptionRequest findInstituteSubscriptionRequest
    ) {
        return subscriptionInstituteMapper.toPageResponseDto(instituteSubscriptionRepository.findAll(
                InstituteSubscriptionSpecification.findWith(subscriptionInstituteMapper.toCriteria(findInstituteSubscriptionRequest)),
                (findInstituteSubscriptionRequest.page() != null && findInstituteSubscriptionRequest.size() != null)
                        ? PageRequest.of(findInstituteSubscriptionRequest.page(), findInstituteSubscriptionRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 구독정보(교육기관) 조회
     * @param instituteSubscriptionId 구독정보(교육기관) 고유번호
     * @return 특정 구독정보(교육기관) 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public InstituteSubscriptionDto.InstituteSubscriptionResponse get(Long instituteSubscriptionId) {
        return subscriptionInstituteMapper.toResponseDto(getInstituteSubscription(instituteSubscriptionId));
    }

    /**
     * 특정 구독정보(교육기관) 수정
     * @param instituteSubscriptionId 수정할 구독정보(교육기관)의 ID
     * @param updateInstituteSubscriptionRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Long instituteSubscriptionId,
            InstituteSubscriptionDto.UpdateInstituteSubscriptionRequest updateInstituteSubscriptionRequest
    ) {
        // 수정할 구독정보(교육기관) 엔티티 조회
        var subscriptionInstitute = getInstituteSubscription(instituteSubscriptionId);
        // 수정 요청 객체에 구독상품 고유번호가 있다면 구독상품 엔티티 조회
        SubscriptionPlan subscriptionPlan = Optional.ofNullable(updateInstituteSubscriptionRequest.subscriptionPlanId())
                .flatMap(subscriptionPlanRepository::findById)
                .orElseGet(subscriptionInstitute::getSubscriptionPlan);
        // 수정 요청 객체에 구독상품을 구독할 교육기관 고유번호가 있다면 교육기관 엔티티 조회
        Institute institute = Optional.ofNullable(updateInstituteSubscriptionRequest.instituteId())
                .flatMap(instituteRepository::findById)
                .orElseGet(subscriptionInstitute::getInstitute);
        // 구독정보(교육기관) 수정
        subscriptionInstituteMapper.updateEntity(
                subscriptionInstitute,
                updateInstituteSubscriptionRequest,
                subscriptionPlan,
                institute
        );
    }

    /**
     * 특정 구독정보(교육기관) 삭제
     * @param instituteSubscriptionId 삭제할 구독정보(교육기관)의 ID
     */
    @Override
    @Transactional
    public void delete(Long instituteSubscriptionId) {
        instituteSubscriptionRepository.delete(getInstituteSubscription(instituteSubscriptionId));
    }

    /**
     * 특정 구독정보(교육기관) 엔티티 조회
     * @param instituteSubscriptionId 구독정보(교육기관) 고유번호
     * @return 특정 구독정보(교육기관) 엔티티 객체
     */
    @Transactional(readOnly = true)
    public InstituteSubscription getInstituteSubscription(Long instituteSubscriptionId) {
        return instituteSubscriptionRepository.findById(instituteSubscriptionId)
                .orElseThrow(() -> new EntityNotFoundException("구독정보(교육기관)를 찾을 수 없습니다. instituteSubscriptionId: " + instituteSubscriptionId));
    }
}