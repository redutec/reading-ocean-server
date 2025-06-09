package com.redutec.admin.inquiry.service;

import com.redutec.core.dto.InquiryDto;
import com.redutec.core.entity.AdminUser;
import com.redutec.core.entity.Inquiry;
import com.redutec.core.mapper.InquiryMapper;
import com.redutec.core.repository.AdminUserRepository;
import com.redutec.core.repository.InquiryRepository;
import com.redutec.core.specification.InquirySpecification;
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
public class InquiryServiceImpl implements InquiryService {
    private final InquiryMapper inquiryMapper;
    private final InquiryRepository inquiryRepository;
    private final AdminUserRepository adminUserRepository;

    /**
     * 고객문의 등록
     * @param createInquiryRequest 고객문의 등록 정보를 담은 DTO
     * @return 등록된 고객문의 정보
     */
    @Override
    @Transactional
    public InquiryDto.InquiryResponse create(InquiryDto.CreateInquiryRequest createInquiryRequest) {
        // 등록 요청 객체에 답변자 아이디가 존재한다면 어드민 사용자 엔티티 조회
        AdminUser responder = Optional.ofNullable(createInquiryRequest.responderAccountId())
                .flatMap(adminUserRepository::findByAccountId)
                .orElse(null);
        // 고객문의 등록
        return inquiryMapper.toResponseDto(inquiryRepository.save(inquiryMapper.toCreateEntity(
                createInquiryRequest,
                responder
        )));
    }

    /**
     * 조건에 맞는 고객문의 목록 조회
     * @param findInquiryRequest 조회 조건을 담은 DTO
     * @return 조회된 고객문의 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public InquiryDto.InquiryPageResponse find(InquiryDto.FindInquiryRequest findInquiryRequest) {
        return inquiryMapper.toPageResponseDto(inquiryRepository.findAll(
                InquirySpecification.findWith(inquiryMapper.toCriteria(findInquiryRequest)),
                (findInquiryRequest.page() != null && findInquiryRequest.size() != null)
                        ? PageRequest.of(findInquiryRequest.page(), findInquiryRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 고객문의 조회
     * @param inquiryId 고객문의 고유번호
     * @return 특정 고객문의 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public InquiryDto.InquiryResponse findById(Long inquiryId) {
        return inquiryMapper.toResponseDto(getInquiry(inquiryId));
    }

    /**
     * 특정 고객문의 수정
     * @param inquiryId 수정할 고객문의의 ID
     * @param updateInquiryRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long inquiryId, InquiryDto.UpdateInquiryRequest updateInquiryRequest) {
        // 수정할 고객문의 엔티티 조회
        Inquiry inquiry = getInquiry(inquiryId);
        // 수정 요청 객체에 답변자 아이디가 존재한다면 어드민 사용자 엔티티 조회
        AdminUser responder = Optional.ofNullable(updateInquiryRequest.responderAccountId())
                .flatMap(adminUserRepository::findByAccountId)
                .orElseGet(inquiry::getResponder);
        // 고객문의 수정
        inquiryRepository.save(inquiryMapper.toUpdateEntity(
                inquiry,
                updateInquiryRequest,
                responder
        ));
    }

    /**
     * 특정 고객문의 삭제
     * @param inquiryId 삭제할 고객문의의 ID
     */
    @Override
    @Transactional
    public void delete(Long inquiryId) {
        inquiryRepository.delete(getInquiry(inquiryId));
    }

    /**
     * 특정 고객문의 엔티티 조회
     * @param inquiryId 고객문의 고유번호
     * @return 특정 고객문의 엔티티 객체
     */
    @Transactional(readOnly = true)
    public Inquiry getInquiry(Long inquiryId) {
        return inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new EntityNotFoundException("고객문의를 찾을 수 없습니다. inquiryId: " + inquiryId));
    }
}