package com.redutec.teachingocean.hqdocument.service;

import com.redutec.core.dto.HeadquartersDocumentDto;
import com.redutec.core.entity.HeadquartersDocument;
import com.redutec.core.mapper.HeadquartersDocumentMapper;
import com.redutec.core.repository.HeadquartersDocumentRepository;
import com.redutec.core.specification.HeadquartersDocumentSpecification;
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
public class HeadquartersDocumentServiceImpl implements HeadquartersDocumentService {
    private final HeadquartersDocumentMapper headquartersDocumentMapper;
    private final HeadquartersDocumentRepository headquartersDocumentRepository;

    /**
     * 조건에 맞는 본사 자료실 목록 조회
     * @param findHeadquartersDocumentRequest 조회 조건을 담은 DTO
     * @return 조회된 본사 자료실 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public HeadquartersDocumentDto.HeadquartersDocumentPageResponse find(
            HeadquartersDocumentDto.FindHeadquartersDocumentRequest findHeadquartersDocumentRequest
    ) {
        return headquartersDocumentMapper.toPageResponseDto(headquartersDocumentRepository.findAll(
                HeadquartersDocumentSpecification.findWith(headquartersDocumentMapper.toCriteria(findHeadquartersDocumentRequest)),
                (findHeadquartersDocumentRequest.page() != null && findHeadquartersDocumentRequest.size() != null)
                        ? PageRequest.of(findHeadquartersDocumentRequest.page(), findHeadquartersDocumentRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 본사 자료실 조회
     * @param headquartersDocumentId 본사 자료실 고유번호
     * @return 특정 본사 자료실 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public HeadquartersDocumentDto.HeadquartersDocumentResponse findById(Long headquartersDocumentId) {
        return headquartersDocumentMapper.toResponseDto(getHeadquartersDocument(headquartersDocumentId));
    }

    /**
     * 특정 본사 자료실 엔티티 조회
     * @param headquartersDocumentId 본사 자료실 고유번호
     * @return 특정 본사 자료실 엔티티 객체
     */
    @Transactional(readOnly = true)
    public HeadquartersDocument getHeadquartersDocument(Long headquartersDocumentId) {
        return headquartersDocumentRepository.findById(headquartersDocumentId)
                .orElseThrow(() -> new EntityNotFoundException("본사 자료실 게시물을 찾을 수 없습니다. headquartersDocumentId: " + headquartersDocumentId));
    }
}