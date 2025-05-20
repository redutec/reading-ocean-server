package com.redutec.admin.product.service;

import com.redutec.core.dto.ProductDto;

public interface ProductService {
    /**
     * 판매상품 등록
     * @param createProductRequest 판매상품 등록 정보를 담은 DTO
     * @return 등록된 판매상품 정보
     */
    ProductDto.ProductResponse create(ProductDto.CreateProductRequest createProductRequest);

    /**
     * 조건에 맞는 판매상품 목록 조회
     * @param findProductRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 판매상품 목록 및 페이징 정보
     */
    ProductDto.ProductPageResponse find(ProductDto.FindProductRequest findProductRequest);

    /**
     * 특정 판매상품 조회
     * @param productId 판매상품 고유번호
     * @return 특정 판매상품 응답 객체
     */
    ProductDto.ProductResponse findById(Long productId);

    /**
     * 특정 판매상품 수정
     * @param productId 수정할 판매상품의 ID
     * @param updateProductRequest 판매상품 수정 요청 객체
     */
    void update(Long productId, ProductDto.UpdateProductRequest updateProductRequest);

    /**
     * 특정 판매상품 삭제
     * @param productId 삭제할 판매상품의 ID
     */
    void delete(Long productId);
}