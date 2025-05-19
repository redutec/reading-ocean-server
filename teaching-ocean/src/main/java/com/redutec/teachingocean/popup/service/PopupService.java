package com.redutec.teachingocean.popup.service;

import com.redutec.core.dto.PopupDto;
import com.redutec.core.entity.Popup;

public interface PopupService {
    /**
     * 조건에 맞는 팝업 목록 조회
     * @param findPopupRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 팝업 목록 및 페이징 정보
     */
    PopupDto.PopupPageResponse find(PopupDto.FindPopupRequest findPopupRequest);

    /**
     * 특정 팝업 조회
     * @param popupId 팝업 고유번호
     * @return 특정 팝업 응답 객체
     */
    PopupDto.PopupResponse findById(Long popupId);

    /**
     * 특정 팝업 엔티티 조회
     * @param popupId 팝업 고유번호
     * @return 특정 팝업 엔티티 객체
     */
    Popup getPopup(Long popupId);
}