package com.redutec.admin.meteredbilling.service;

import com.redutec.core.dto.MeteredBillingDto;
import com.redutec.core.entity.MeteredBilling;
import com.redutec.core.mapper.MeteredBillingMapper;
import com.redutec.core.repository.MeteredBillingRepository;
import com.redutec.core.specification.MeteredBillingSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class MeteredBillingServiceImpl implements MeteredBillingService {
    private final MeteredBillingMapper meteredBillingMapper;
    private final MeteredBillingRepository meteredBillingRepository;

    /**
     * 조건에 맞는 월별 사용료 청구서 목록 조회
     * @param findMeteredBillingRequest 조회 조건을 담은 DTO
     * @return 조회된 월별 사용료 청구서 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public MeteredBillingDto.MeteredBillingPageResponse find(MeteredBillingDto.FindMeteredBillingRequest findMeteredBillingRequest) {
        return meteredBillingMapper.toPageResponseDto(meteredBillingRepository.findAll(
                MeteredBillingSpecification.findWith(meteredBillingMapper.toCriteria(findMeteredBillingRequest)),
                (findMeteredBillingRequest.page() != null && findMeteredBillingRequest.size() != null)
                        ? PageRequest.of(findMeteredBillingRequest.page(), findMeteredBillingRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 월별 사용료 청구서 조회
     * @param meteredBillingId 월별 사용료 청구서 고유번호
     * @return 특정 월별 사용료 청구서 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public MeteredBillingDto.MeteredBillingResponse get(Long meteredBillingId) {
        return meteredBillingMapper.toResponseDto(getMeteredBilling(meteredBillingId));
    }

    /**
     * 특정 월별 사용료 청구서 엔티티 조회
     * @param meteredBillingId 월별 사용료 청구서 고유번호
     * @return 특정 월별 사용료 청구서 엔티티 객체
     */
    @Transactional(readOnly = true)
    public MeteredBilling getMeteredBilling(Long meteredBillingId) {
        return meteredBillingRepository.findById(meteredBillingId)
                .orElseThrow(() -> new EntityNotFoundException("월별 사용료 청구서를 찾을 수 없습니다. meteredBillingId: " + meteredBillingId));
    }
}