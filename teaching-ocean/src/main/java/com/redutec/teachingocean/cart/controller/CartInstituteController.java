package com.redutec.teachingocean.cart.controller;

import com.redutec.teachingocean.cart.service.CartInstituteService;
import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.CartInstituteDto;
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
@RequestMapping("/cart")
@Tag(name = "장바구니(교육기관) API", description = "장바구니(교육기관) API 모음")
public class CartInstituteController {
    private final ApiResponseManager apiResponseManager;
    private final CartInstituteService cartInstituteService;

    @Operation(summary = "티칭오션몰 - 특정 상품 - 장바구니", description = "장바구니에 선택한 상품들을 추가")
    @PostMapping
    public ResponseEntity<ApiResponseBody> addItems(
            @ParameterObject @Valid CartInstituteDto.AddItemToCartInstituteRequest addItemToCartInstituteRequest
    ) {
        return apiResponseManager.create(cartInstituteService.create(addItemToCartInstituteRequest));
    }

    @Operation(summary = "조건에 맞는 장바구니(교육기관) 목록 조회", description = "조건에 맞는 장바구니(교육기관) 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid CartInstituteDto.FindCartInstituteRequest findCartInstituteRequest
    ) {
        return apiResponseManager.ok(cartInstituteService.find(findCartInstituteRequest));
    }

    @Operation(summary = "특정 장바구니(교육기관) 조회", description = "특정 장바구니(교육기관)을 조회하는 API")
    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponseBody> findById(@PathVariable Long cartId) {
        return apiResponseManager.ok(cartInstituteService.findById(cartId));
    }

    @Operation(summary = "특정 장바구니(교육기관) 수정", description = "특정 장바구니(교육기관)을 수정하는 API")
    @PatchMapping(path = "/{cartId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "장바구니(교육기관) ID") @PathVariable Long cartId,
            @ModelAttribute @Valid CartInstituteDto.UpdateCartRequest updateCartRequest
    ) {
        cartInstituteService.update(cartId, updateCartRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 장바구니(교육기관) 삭제", description = "특정 장바구니(교육기관)을 삭제하는 API")
    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponseBody> delete(@Parameter(description = "장바구니(교육기관) ID") @PathVariable Long cartId) {
        cartInstituteService.delete(cartId);
        return apiResponseManager.noContent();
    }
}