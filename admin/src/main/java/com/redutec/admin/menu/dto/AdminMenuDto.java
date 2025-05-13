package com.redutec.admin.menu.dto;

import com.redutec.core.meta.AdminUserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public class AdminMenuDto {
    @Schema(description = "어드민 메뉴 등록 요청 객체")
    public record CreateAdminMenuRequest(
            @Schema(description = "메뉴명", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 50, message = "메뉴명은 50자를 넘을 수 없습니다")
            String name,

            @Schema(description = "접근 URL", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @Size(max = 30, message = "접근 URL은 30자를 넘을 수 없습니다")
            String url,

            @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description,

            @Schema(description = "사용여부", requiredMode = Schema.RequiredMode.REQUIRED, example = "Y")
            @NotNull
            Boolean available,

            @Schema(description = "접근 가능한 권한", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @ElementCollection(targetClass = AdminUserRole.class)
            @Enumerated(EnumType.STRING)
            List<AdminUserRole> accessibleRoles,

            @Schema(description = "메뉴의 깊이(최상위: 0)", requiredMode = Schema.RequiredMode.REQUIRED)
            @NotNull
            @PositiveOrZero
            Integer depth,

            @Schema(description = "상위 메뉴 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Positive
            Long parentMenuId,

            @Schema(description = "소속된 하위 메뉴 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> childrenMenuIds
    ) {}

    @Schema(description = "어드민 메뉴 조회 요청 객체")
    public record FindAdminMenuRequest(
            @Schema(description = "어드민 메뉴 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> adminMenuIds,

            @Schema(description = "메뉴명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 50, message = "메뉴명은 50자를 넘을 수 없습니다")
            String name,

            @Schema(description = "닉네임", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 30, message = "접근 URL은 30자를 넘을 수 없습니다")
            String url,

            @Schema(description = "사용여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            Boolean available,

            @Schema(description = "접근 가능한 권한", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = AdminUserRole.class)
            @Enumerated(EnumType.STRING)
            List<AdminUserRole> accessibleRoles,

            @Schema(description = "페이지 번호(0 이상의 정수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "0")
            @PositiveOrZero
            Integer page,

            @Schema(description = "페이지 당 데이터 개수(1 이상의 자연수)", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "60")
            @Positive
            Integer size
    ) {}

    @Schema(description = "어드민 메뉴 수정 요청 객체")
    public record UpdateAdminMenuRequest(
            @Schema(description = "메뉴명", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 50, message = "메뉴명은 50자를 넘을 수 없습니다")
            String name,

            @Schema(description = "접근 URL", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Size(max = 30, message = "접근 URL은 30자를 넘을 수 없습니다")
            String url,

            @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            String description,

            @Schema(description = "사용여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "Y")
            Boolean available,

            @Schema(description = "접근 가능한 권한", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @ElementCollection(targetClass = AdminUserRole.class)
            @Enumerated(EnumType.STRING)
            List<AdminUserRole> accessibleRoles,

            @Schema(description = "메뉴의 깊이(최상위: 0)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @PositiveOrZero
            Integer depth,

            @Schema(description = "상위 메뉴 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            @Positive
            Long parentMenuId,

            @Schema(description = "소속된 하위 메뉴 ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
            List<@Positive Long> childrenMenuIds
    ) {}

    @Schema(description = "어드민 메뉴 응답 객체")
    public record AdminMenuResponse(
            Long adminMenuId,
            String name,
            String url,
            String description,
            Boolean available,
            List<AdminUserRole> accessibleRoles,
            Integer depth,
            AdminMenuDto.AdminMenuResponse parentMenu,
            List<AdminMenuDto.AdminMenuResponse> childrenMenus,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}

    @Schema(description = "어드민 메뉴 응답 페이징 객체")
    public record AdminMenuPageResponse(
            List<AdminMenuResponse> adminMenus,
            Long totalElements,
            Integer totalPages
    ) {}
}