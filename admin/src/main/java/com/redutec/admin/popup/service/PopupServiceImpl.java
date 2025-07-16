package com.redutec.admin.popup.service;

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
     * 팝업 등록
     * @param createPopupRequest 팝업 등록 정보를 담은 DTO
     * @return 등록된 팝업 정보
     */
    @Override
    @Transactional
    public PopupDto.PopupResponse create(PopupDto.CreatePopupRequest createPopupRequest) {
        return popupMapper.toResponseDto(popupRepository.save(popupMapper.createEntity(createPopupRequest)));
    }

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
    public PopupDto.PopupResponse get(Long popupId) {
        return popupMapper.toResponseDto(getPopup(popupId));
    }

    /**
     * 특정 팝업 수정
     * @param popupId 수정할 팝업의 ID
     * @param updatePopupRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long popupId, PopupDto.UpdatePopupRequest updatePopupRequest) {
        popupMapper.updateEntity(getPopup(popupId), updatePopupRequest);
    }

    /**
     * 특정 팝업 삭제
     * @param popupId 삭제할 팝업의 ID
     */
    @Override
    @Transactional
    public void delete(Long popupId) {
        popupRepository.delete(getPopup(popupId));
    }

    /**
     * 특정 팝업 엔티티 조회
     * @param popupId 팝업 고유번호
     * @return 특정 팝업 엔티티 객체
     */
    @Transactional(readOnly = true)
    public Popup getPopup(Long popupId) {
        return popupRepository.findById(popupId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 팝업입니다. popupId: " + popupId));
    }
}