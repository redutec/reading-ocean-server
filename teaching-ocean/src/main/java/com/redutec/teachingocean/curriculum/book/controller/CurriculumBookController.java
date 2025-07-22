package com.redutec.teachingocean.curriculum.book.controller;

import com.redutec.core.config.ApiResponseBody;
import com.redutec.core.config.ApiResponseManager;
import com.redutec.core.dto.CurriculumBookDto;
import com.redutec.teachingocean.curriculum.book.service.CurriculumBookService;
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
@RequestMapping("/curriculums/books")
@Tag(name = "커리큘럼에 소속할 도서 관리 API", description = "커리큘럼에 소속할 도서 관리 API 모음")
public class CurriculumBookController {
    private final ApiResponseManager apiResponseManager;
    private final CurriculumBookService curriculumBookService;

    @Operation(summary = "커리큘럼에 소속할 도서 등록", description = "커리큘럼에 소속할 도서를 등록하는 API")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> create(
            @ModelAttribute @Valid CurriculumBookDto.CreateCurriculumBookRequest createCurriculumBookRequest
    ) {
        return apiResponseManager.create(curriculumBookService.create(createCurriculumBookRequest));
    }
    
    @Operation(summary = "조건에 맞는 커리큘럼에 소속한 도서 목록 조회", description = "조건에 맞는 커리큘럼에 소속한 도서 목록을 조회하는 API")
    @GetMapping
    public ResponseEntity<ApiResponseBody> find(
            @ParameterObject @Valid CurriculumBookDto.FindCurriculumBookRequest findCurriculumBookRequest
    ) {
        return apiResponseManager.ok(curriculumBookService.find(findCurriculumBookRequest));
    }

    @Operation(summary = "특정 커리큘럼에 소속한 도서 조회", description = "특정 커리큘럼에 소속한 도서를 조회하는 API")
    @GetMapping("/{curriculumBookId}")
    public ResponseEntity<ApiResponseBody> get(@PathVariable Long curriculumBookId) {
        return apiResponseManager.ok(curriculumBookService.get(curriculumBookId));
    }

    @Operation(summary = "특정 커리큘럼에 소속한 도서 수정", description = "특정 커리큘럼에 소속한 도서를 수정하는 API")
    @PutMapping(path = "/{curriculumBookId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseBody> update(
            @Parameter(description = "커리큘럼에 소속할 도서 ID") @PathVariable Long curriculumBookId,
            @ModelAttribute @Valid CurriculumBookDto.UpdateCurriculumBookRequest updateCurriculumBookRequest
    ) {
        curriculumBookService.update(curriculumBookId, updateCurriculumBookRequest);
        return apiResponseManager.noContent();
    }

    @Operation(summary = "특정 커리큘럼에 소속한 도서 삭제", description = "특정 커리큘럼에 소속한 도서를 삭제하는 API")
    @DeleteMapping("/{curriculumBookId}")
    public ResponseEntity<ApiResponseBody> delete(
            @Parameter(description = "커리큘럼에 소속할 도서 ID") @PathVariable Long curriculumBookId
    ) {
        curriculumBookService.delete(curriculumBookId);
        return apiResponseManager.noContent();
    }
}