package com.redutec.admin.v1.backoffice.dto;

import com.redutec.core.meta.v1.PermissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class BackOfficeGroupDto {
    @Schema(description = "관리자 그룹 등록 요청 객체")
    public record CreateBackOfficeGroupRequest(
        @Schema(description = "관리자 그룹 이름", requiredMode = Schema.RequiredMode.REQUIRED, example = "테스트 관리자 그룹")
        @NotBlank
        @Size(max = 100)
        String groupName,

        @Schema(description = "비고", example = "테스트")
        @Size(max = 300)
        String description,

        @Schema(description = "사용 여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "N")
        @NotBlank
        @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
        String useYn
    ) {}

    @Schema(description = "관리자 그룹 조회 요청 객체")
    public record FindBackOfficeGroupRequest(
        @Schema(description = "관리자 그룹 고유번호 목록", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        List<@Positive Integer> groupNoList,

        @Schema(description = "관리자 그룹 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String groupName,

        @Schema(description = "페이지 번호 (0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @PositiveOrZero
        Integer page,

        @Schema(description = "페이지당 row의 개수 (1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @PositiveOrZero
        Integer size
    ) {}

    @Schema(description = "관리자 그룹 수정 요청 객체")
    public record UpdateBackOfficeGroupRequest(
        @Schema(description = "관리자 그룹 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "테스트 관리자 그룹")
        @Size(max = 100)
        String groupName,

        @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "테스트")
        @Size(max = 300)
        String description,

        @Schema(description = "사용 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Y")
        @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
        String useYn,

        @Schema(description = "그룹 권한 요청 목록", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        List<BackOfficeGroupPermissionRequest> groupPermissions
    ) {}

    @Schema(description = "그룹 권한 요청 객체 (업데이트 시 사용)")
    public record BackOfficeGroupPermissionRequest(
        @Schema(description = "메뉴 번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1001")
        Long menuNo,

        @Schema(description = "권한 타입", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "PMT001")
        PermissionType permissionType,

        @Schema(description = "사용 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Y")
        @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
        String useYn
    ) {}

    @Schema(description = "관리자 그룹 권한 응답 객체")
    public record BackOfficeGroupPermissionResponse(
        Integer menuNo,
        Integer groupNo,
        PermissionType permissionType,
        LocalDateTime registerDatetime,
        LocalDateTime modifyDatetime
    ) {}

    @Schema(description = "관리자 그룹 응답 객체")
    public record BackOfficeGroupResponse(
        Integer groupNo,
        String groupName,
        String useYn,
        LocalDateTime registerDatetime,
        LocalDateTime modifyDatetime,
        String description,
        List<Integer> userGroups
    ) {}

    @Schema(description = "관리자 그룹 응답 객체(접근 권한 포함)")
    public record BackOfficeGroupWithPermissionResponse(
        Integer groupNo,
        String groupName,
        String useYn,
        LocalDateTime registerDatetime,
        LocalDateTime modifyDatetime,
        String description,
        List<Integer> userGroups,
        List<BackOfficeGroupPermissionResponse> groupPermissions
    ) {}

    @Schema(description = "관리자 그룹 목록 및 페이징 정보 응답 객체")
    public record BackOfficeGroupPageResponse(
        List<BackOfficeGroupResponse> botGroupList,
        Long totalElements,
        Integer totalPages
    ) {}
}