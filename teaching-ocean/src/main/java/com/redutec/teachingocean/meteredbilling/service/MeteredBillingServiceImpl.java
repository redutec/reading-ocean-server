package com.redutec.teachingocean.meteredbilling.service;

import com.redutec.core.dto.MeteredBillingDto;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.MeteredBilling;
import com.redutec.core.mapper.MeteredBillingMapper;
import com.redutec.core.repository.InstituteRepository;
import com.redutec.core.repository.MeteredBillingRepository;
import com.redutec.core.specification.MeteredBillingSpecification;
import com.redutec.teachingocean.authentication.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class MeteredBillingServiceImpl implements MeteredBillingService {
    private final MeteredBillingMapper meteredBillingMapper;
    private final MeteredBillingRepository meteredBillingRepository;
    private final AuthenticationService authenticationService;
    private final InstituteRepository instituteRepository;

    /**
     * 현재 로그인한 교사가 속한 교육기관의 월별 사용료 청구서 목록 조회
     * @param findMeteredBillingRequest 조회 조건을 담은 DTO
     * @return 현재 로그인한 교사가 속한 교육기관의 월별 사용료 청구서 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public MeteredBillingDto.MeteredBillingPageResponse find(MeteredBillingDto.FindMeteredBillingRequest findMeteredBillingRequest) {
        // 현재 로그인한 교사의 교육기관 고유번호 Criteria에 적용하여 새로운 레코드로 생성
        var filteredRequest = new MeteredBillingDto.FindMeteredBillingRequest(
                findMeteredBillingRequest.meteredBillingIds(),
                List.of(authenticationService.getAuthenticatedTeacher().instituteId()),
                findMeteredBillingRequest.billingPeriodMonths(),
                findMeteredBillingRequest.billingStatuses(),
                findMeteredBillingRequest.page(),
                findMeteredBillingRequest.size()
        );
        // 페이지 번호(page)와 페이지 크기(size)가 모두 null이 아니면 PageRequest 생성(그렇지 않으면 pageable 처리 하지 않음)
        Pageable pageable = Optional.ofNullable(filteredRequest.page())
                .flatMap(page -> Optional.ofNullable(filteredRequest.size())
                        .map(size -> (Pageable) PageRequest.of(page, size)))
                .orElse(Pageable.unpaged());
        // Specification, pageable을 사용하여 엔티티 목록 조회
        var meteredBillings = meteredBillingRepository.findAll(
                MeteredBillingSpecification.findWith(meteredBillingMapper.toCriteria(filteredRequest)),
                pageable
        );
        // 조회한 엔티티 목록을 응답 객체로 변환하여 리턴
        return meteredBillingMapper.toPageResponseDto(meteredBillings);
    }

    /**
     * 특정 월별 사용료 청구서 조회(현재 로그인한 교사의 교육기관에 대한 월별 사용료 청구서라면 Null로 응답)
     * @param meteredBillingId 월별 사용료 청구서 고유번호
     * @return 특정 월별 사용료 청구서 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public MeteredBillingDto.MeteredBillingResponse get(Long meteredBillingId) {
        return meteredBillingMapper.toResponseDto(getMeteredBilling(meteredBillingId));
    }

    /**
     * 특정 월별 사용료 청구서 엔티티 조회(현재 로그인한 교사의 교육기관에 대한 월별 사용료 청구서라면 Null로 응답)
     * @param meteredBillingId 월별 사용료 청구서 고유번호
     * @return 특정 월별 사용료 청구서 엔티티 객체
     */
    @Transactional(readOnly = true)
    public MeteredBilling getMeteredBilling(Long meteredBillingId) {
        // 현재 로그인한 교사의 교육기관 엔티티 조회
        Long instituteId = authenticationService.getAuthenticatedTeacher().instituteId();
        Institute institute = instituteRepository.findById(instituteId)
                .orElseThrow(() -> new EntityNotFoundException("교육기관이 존재하지 않습니다. instituteId: " + instituteId));
        // 월별 사용료 청구서 엔티티 조회 후 리턴
        return meteredBillingRepository.findByIdAndInstitute(meteredBillingId, institute)
                .orElseThrow(() -> new EntityNotFoundException("월별 사용료 청구서를 찾을 수 없습니다. meteredBillingId: " + meteredBillingId));
    }
}