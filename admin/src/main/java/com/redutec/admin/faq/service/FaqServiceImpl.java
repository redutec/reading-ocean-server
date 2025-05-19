package com.redutec.admin.faq.service;

import com.redutec.core.dto.FaqDto;
import com.redutec.core.mapper.FaqMapper;
import com.redutec.core.entity.Faq;
import com.redutec.core.repository.FaqRepository;
import com.redutec.core.specification.FaqSpecification;
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
public class FaqServiceImpl implements FaqService {
    private final FaqMapper faqMapper;
    private final FaqRepository faqRepository;

    /**
     * 이용안내 등록
     * @param createFaqRequest 이용안내 등록 정보를 담은 DTO
     * @return 등록된 이용안내 정보
     */
    @Override
    @Transactional
    public FaqDto.FaqResponse create(FaqDto.CreateFaqRequest createFaqRequest) {
        return faqMapper.toResponseDto(faqRepository.save(faqMapper.toCreateEntity(createFaqRequest)));
    }

    /**
     * 조건에 맞는 이용안내 목록 조회
     * @param findFaqRequest 조회 조건을 담은 DTO
     * @return 조회된 이용안내 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public FaqDto.FaqPageResponse find(FaqDto.FindFaqRequest findFaqRequest) {
        return faqMapper.toPageResponseDto(faqRepository.findAll(
                FaqSpecification.findWith(faqMapper.toCriteria(findFaqRequest)),
                (findFaqRequest.page() != null && findFaqRequest.size() != null)
                        ? PageRequest.of(findFaqRequest.page(), findFaqRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 이용안내 조회
     * @param faqId 이용안내 고유번호
     * @return 특정 이용안내 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public FaqDto.FaqResponse findById(Long faqId) {
        return faqMapper.toResponseDto(getFaq(faqId));
    }

    /**
     * 특정 이용안내 수정
     * @param faqId 수정할 이용안내의 ID
     * @param updateFaqRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long faqId, FaqDto.UpdateFaqRequest updateFaqRequest) {
        faqRepository.save(faqMapper.toUpdateEntity(getFaq(faqId), updateFaqRequest));
    }

    /**
     * 특정 이용안내 삭제
     * @param faqId 삭제할 이용안내의 ID
     */
    @Override
    @Transactional
    public void delete(Long faqId) {
        faqRepository.delete(getFaq(faqId));
    }

    /**
     * 특정 이용안내 엔티티 조회
     * @param faqId 이용안내 고유번호
     * @return 특정 이용안내 엔티티 객체
     */
    @Transactional(readOnly = true)
    public Faq getFaq(Long faqId) {
        return faqRepository.findById(faqId)
                .orElseThrow(() -> new EntityNotFoundException("이용안내를 찾을 수 없습니다. faqId = " + faqId));
    }
}