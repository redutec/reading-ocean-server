package com.redutec.teachingocean.popup.service;

import com.redutec.core.dto.PopupDto;
import com.redutec.core.entity.Popup;
import com.redutec.core.mapper.PopupMapper;
import com.redutec.core.repository.PopupRepository;
import com.redutec.core.specification.PopupSpecification;
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
public class PopupServiceImpl implements PopupService {
    private final PopupMapper popupMapper;
    private final PopupRepository popupRepository;

    /**
     * 조건에 맞는 팝업 목록 조회
     * @param findPopupRequest 조회 조건을 담은 DTO
     * @return 조회된 팝업 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public PopupDto.PopupPageResponse find(PopupDto.FindPopupRequest findPopupRequest) {
        return popupMapper.toPageResponseDto(popupRepository.findAll(
                PopupSpecification.findWith(popupMapper.toCriteria(findPopupRequest)),
                (findPopupRequest.page() != null && findPopupRequest.size() != null)
                        ? PageRequest.of(findPopupRequest.page(), findPopupRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 팝업 조회
     * @param popupId 팝업 고유번호
     * @return 특정 팝업 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public PopupDto.PopupResponse findById(Long popupId) {
        return popupMapper.toResponseDto(getPopup(popupId));
    }

    /**
     * 특정 팝업 엔티티 조회
     * @param popupId 팝업 고유번호
     * @return 특정 팝업 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Popup getPopup(Long popupId) {
        return popupRepository.findById(popupId)
                .orElseThrow(() -> new EntityNotFoundException("팝업을 찾을 수 없습니다. popupId = " + popupId));
    }
}