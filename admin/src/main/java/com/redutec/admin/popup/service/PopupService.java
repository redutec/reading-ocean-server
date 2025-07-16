package com.redutec.admin.popup.service;

import com.redutec.core.dto.PopupDto;

public interface PopupService {
    /**
     * 팝업 등록
     * @param createPopupRequest 팝업 등록 정보를 담은 DTO
     * @return 등록된 팝업 정보
     */
    PopupDto.PopupResponse create(PopupDto.CreatePopupRequest createPopupRequest);

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
    PopupDto.PopupResponse get(Long popupId);

    /**
     * 특정 팝업 수정
     * @param popupId 수정할 팝업의 ID
     * @param updatePopupRequest 팝업 수정 요청 객체
     */
    void update(Long popupId, PopupDto.UpdatePopupRequest updatePopupRequest);

    /**
     * 특정 팝업 삭제
     * @param popupId 삭제할 팝업의 ID
     */
    void delete(Long popupId);
}