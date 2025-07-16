package com.redutec.teachingocean.mall.product.service;

import com.redutec.core.dto.ProductDto;

public interface ProductService {
    /**
     * 리딩오션몰 - 상품 목록
     * @param findProductRequest 리딩오션몰의 상품 목록 조회 요청 객체
     * @return 조건에 맞는 리딩오션몰의 상품 목록 응답 객체
     */
    ProductDto.ProductPageResponse find(ProductDto.FindProductRequest findProductRequest);

    /**
     * 리딩오션몰 - 상품 목록 - 특정 상품 조회
     * @param productId 특정 상품의 고유번호
     * @return 리딩오션몰의 특정 상품 정보
     */
    ProductDto.ProductResponse get(Long productId);
}