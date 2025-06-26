package com.redutec.admin.product.controller;

import com.redutec.core.dto.ProductDto;
import com.redutec.admin.product.service.ProductService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "판매상품 관리 API", description = "판매상품 관리 API 모음")
public class ProductController {
    private final ApiResponseManager apiResponseManager;
    private final ProductService productService;

    @Operation(summary = "판매상품 등록", description = "판매상품 정보를 등록하는 API")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> create(
            @ModelAttribute @Valid ProductDto.CreateProductRequest createProductRequest
    ) {
        return apiResponseManager.create(productService.create(createProductRequest));
    }

    @Operation(summary = "조건에 맞는 판매상품 목록 조회", description = "조건에 맞는 판매상품 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid ProductDto.FindProductRequest findProductRequest
    ) {
        return apiResponseManager.ok(productService.find(findProductRequest));
    }

    @Operation(summary = "특정 판매상품 조회", description = "특정 판매상품을 조회하는 API")
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long productId) {
        return apiResponseManager.ok(productService.findById(productId));
    }

    @Operation(summary = "특정 판매상품 수정", description = "특정 판매상품을 수정하는 API")
    @PutMapping(path = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "판매상품 ID") @PathVariable Long productId,
            @ModelAttribute @Valid ProductDto.UpdateProductRequest updateProductRequest
    ) {
        productService.update(productId, updateProductRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 판매상품 삭제", description = "특정 판매상품을 삭제하는 API")
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseBody> delete(@Parameter(description = "판매상품 ID") @PathVariable Long productId) {
        productService.delete(productId);
        return apiResponseManager.noContent();
    }
}