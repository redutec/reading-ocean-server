package com.redutec.teachingocean.mall.product.service;

import com.redutec.core.dto.ProductDto;
import com.redutec.core.mapper.ProductMapper;
import com.redutec.core.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    /**
     * 리딩오션몰 - 상품 목록
     *
     * @param findProductRequest 리딩오션몰의 상품 목록 조회 요청 객체
     * @return 조건에 맞는 리딩오션몰의 상품 목록 응답 객체
     */
    @Override
    public ProductDto.ProductPageResponse find(ProductDto.FindProductRequest findProductRequest) {
        return null;
    }

    /**
     * 리딩오션몰 - 상품 목록 - 특정 상품 조회
     *
     * @param productId 특정 상품의 고유번호
     * @return 리딩오션몰의 특정 상품 정보
     */
    @Override
    public ProductDto.ProductResponse findById(Long productId) {
        return null;
    }
}