package com.redutec.teachingocean.mall.product.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.ProductDto;
import com.redutec.teachingocean.mall.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "상품 API", description = "상품 API 모음")
public class ProductController {
    private final ApiResponseManager apiResponseManager;
    private final ProductService productService;

    @Operation(summary = "리딩오션몰 - 상품 목록", description = "리딩오션몰의 상품 목록을 조회")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid ProductDto.FindProductRequest findProductRequest
    ) {
        return apiResponseManager.ok(productService.find(findProductRequest));
    }

    @Operation(summary = "리딩오션몰 - 상품 목록 - 특정 상품 조회", description = "리딩오션몰의 특정 상품 조회")
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponseBody> get(@Parameter(description = "상품 ID") @PathVariable Long productId) {
        return apiResponseManager.ok(productService.get(productId));
    }
}