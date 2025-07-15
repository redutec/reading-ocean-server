package com.redutec.admin.meteredbilling.service;

import com.redutec.core.dto.MeteredBillingDto;

public interface MeteredBillingService {
    /**
     * 조건에 맞는 월별 사용료 청구서 목록 조회
     * @param findMeteredBillingRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 월별 사용료 청구서 목록 및 페이징 정보
     */
    MeteredBillingDto.MeteredBillingPageResponse find(MeteredBillingDto.FindMeteredBillingRequest findMeteredBillingRequest);

    /**
     * 특정 월별 사용료 청구서 조회
     * @param meteredBillingId 월별 사용료 청구서 고유번호
     * @return 특정 월별 사용료 청구서 응답 객체
     */
    MeteredBillingDto.MeteredBillingResponse findById(Long meteredBillingId);
}