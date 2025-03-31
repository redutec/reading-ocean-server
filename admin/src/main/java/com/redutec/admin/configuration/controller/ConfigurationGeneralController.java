package com.redutec.admin.configuration.controller;

import com.redutec.admin.configuration.dto.ConfigurationGeneralDto;
import com.redutec.admin.configuration.service.ConfigurationGeneralService;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 사이트 설정 API 컨트롤러.
 * 이 클래스는 사이트 설정 관련 API 엔드포인트를 정의합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/configuration/general")
@Tag(name = "사이트 설정 관리", description = "사이트 설정 관련 API 모음")
public class ConfigurationGeneralController {
    private final ApiResponseManager apiResponseManager;
    private final ConfigurationGeneralService configurationGeneralService;

    @Operation(summary = "사이트 설정 등록", description = "새로운 사이트 설정 정보를 등록하는 API")
    @PostMapping
    public ResponseEntity<ApiResponseDto> create(@Valid @RequestBody ConfigurationGeneralDto.CreateConfigurationGeneralRequest createConfigurationGeneralRequest) {
        return apiResponseManager.success(configurationGeneralService.create(createConfigurationGeneralRequest));
    }

    @Operation(summary = "사이트 설정 목록 조회", description = "조건에 맞는 사이트 설정 정보를 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseDto> find(
            @Parameter(description = "설정 키 목록") @RequestParam(required = false) List<String> configurationKeyList,
            @Parameter(description = "설정 카테고리 키") @RequestParam(required = false) String configurationCategoryKey,
            @Parameter(description = "페이지 번호", example = "0") @RequestParam(required = false) Integer page,
            @Parameter(description = "페이지 당 데이터 개수", example = "30") @RequestParam(required = false) Integer size
    ) {
        ConfigurationGeneralDto.FindConfigurationGeneralRequest findConfigurationGeneralRequest = ConfigurationGeneralDto.FindConfigurationGeneralRequest.builder()
                .configurationKeyList(configurationKeyList)
                .configurationCategoryKey(configurationCategoryKey)
                .page(page)
                .size(size)
                .build();
        return apiResponseManager.success(configurationGeneralService.find(findConfigurationGeneralRequest));
    }

    @Operation(summary = "사이트 설정 상세 조회", description = "특정 사이트 설정 정보를 상세 조회하는 API")
    @GetMapping("/{configurationKey}")
    public ResponseEntity<ApiResponseDto> findByConfigurationKey(@PathVariable String configurationKey) {
        return apiResponseManager.success(configurationGeneralService.findByConfigurationKey(configurationKey));
    }

    @Operation(summary = "사이트 설정 수정", description = "특정 사이트 설정 정보를 수정하는 API")
    @PutMapping("/{configurationKey}")
    public ResponseEntity<ApiResponseDto> update(
            @PathVariable String configurationKey,
            @Valid @RequestBody ConfigurationGeneralDto.UpdateConfigurationGeneralRequest updateConfigurationGeneralRequest) {
        configurationGeneralService.update(configurationKey, updateConfigurationGeneralRequest);
        return apiResponseManager.success(null);
    }

    @Operation(summary = "사이트 설정 삭제", description = "특정 사이트 설정 정보를 삭제하는 API")
    @DeleteMapping("/{configurationKey}")
    public ResponseEntity<ApiResponseDto> delete(@PathVariable String configurationKey) {
        configurationGeneralService.delete(configurationKey);
        return apiResponseManager.success(null);
    }
}