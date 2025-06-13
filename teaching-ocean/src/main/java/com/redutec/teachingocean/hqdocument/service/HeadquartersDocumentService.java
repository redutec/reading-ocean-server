package com.redutec.teachingocean.hqdocument.service;

import com.redutec.core.dto.HeadquartersDocumentDto;

public interface HeadquartersDocumentService {
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
}