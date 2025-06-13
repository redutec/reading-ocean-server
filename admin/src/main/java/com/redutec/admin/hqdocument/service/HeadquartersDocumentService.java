package com.redutec.admin.hqdocument.service;

import com.redutec.core.dto.HeadquartersDocumentDto;

public interface HeadquartersDocumentService {
    /**
     * 본사 자료실 게시물 등록
     * @param createHeadquartersDocumentRequest 본사 자료실 게시물 등록 정보를 담은 DTO
     * @return 등록된 본사 자료실 게시물 정보
     */
    HeadquartersDocumentDto.HeadquartersDocumentResponse create(HeadquartersDocumentDto.CreateHeadquartersDocumentRequest createHeadquartersDocumentRequest);

    /**
     * 조건에 맞는 본사 자료실 게시물 목록 조회
     * @param findHeadquartersDocumentRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 본사 자료실 게시물 목록 및 페이징 정보
     */
    HeadquartersDocumentDto.HeadquartersDocumentPageResponse find(HeadquartersDocumentDto.FindHeadquartersDocumentRequest findHeadquartersDocumentRequest);

    /**
     * 특정 본사 자료실 게시물 조회
     * @param headquartersDocumentId 본사 자료실 게시물 고유번호
     * @return 특정 본사 자료실 게시물 응답 객체
     */
    HeadquartersDocumentDto.HeadquartersDocumentResponse findById(Long headquartersDocumentId);

    /**
     * 특정 본사 자료실 게시물 수정
     * @param headquartersDocumentId 수정할 본사 자료실 게시물의 ID
     * @param updateHeadquartersDocumentRequest 본사 자료실 게시물 수정 요청 객체
     */
    void update(Long headquartersDocumentId, HeadquartersDocumentDto.UpdateHeadquartersDocumentRequest updateHeadquartersDocumentRequest);

    /**
     * 특정 본사 자료실 게시물 삭제
     * @param headquartersDocumentId 삭제할 본사 자료실 게시물의 ID
     */
    void delete(Long headquartersDocumentId);
}