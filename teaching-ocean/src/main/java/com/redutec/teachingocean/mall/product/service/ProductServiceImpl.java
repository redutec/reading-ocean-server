package com.redutec.teachingocean.mall.product.service;

import com.redutec.core.dto.ProductDto;
import com.redutec.core.entity.Product;
import com.redutec.core.mapper.ProductMapper;
import com.redutec.core.repository.ProductRepository;
import com.redutec.core.specification.ProductSpecification;
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
    @Transactional(readOnly = true)
    public ProductDto.ProductPageResponse find(ProductDto.FindProductRequest findProductRequest) {
        return productMapper.toPageResponseDto(productRepository.findAll(
                ProductSpecification.findWith(productMapper.toCriteria(findProductRequest)),
                (findProductRequest.page() != null && findProductRequest.size() != null)
                        ? PageRequest.of(findProductRequest.page(), findProductRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 리딩오션몰 - 상품 목록 - 특정 상품 조회
     *
     * @param productId 특정 상품의 고유번호
     * @return 리딩오션몰의 특정 상품 정보
     */
    @Override
    @Transactional(readOnly = true)
    public ProductDto.ProductResponse get(Long productId) {
        return productMapper.toResponseDto(
                getProduct(productId)
        );
    }

    /**
     * 특정 판매상품 엔티티 조회
     * @param productId 판매상품 고유번호
     * @return 특정 판매상품 엔티티 객체
     */
    @Transactional(readOnly = true)
    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("판매상품을 찾을 수 없습니다. productId: " + productId));
    }
}