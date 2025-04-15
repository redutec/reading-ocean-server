package com.redutec.admin.backoffice.dto;

import com.redutec.core.entity.v1.BotUserGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class BackOfficeUserDto {
    @Schema(description = "관리자 계정 등록 요청 객체")
    public record CreateBackOfficeUserRequest(
        @Schema(description = "관리자 계정 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(min = 4, max = 100)
        String userId,

        @Schema(description = "관리자 이름", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Size(max = 20)
        String userName,

        @Schema(description = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$",
                message = "비밀번호는 최소 8자리 이상이며, 대문자, 소문자, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다."
        )
        String password,

        @Schema(description = "관리자 그룹 고유번호", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank
        @Positive
        Integer groupNo,

        @Schema(description = "비고", requiredMode = Schema.RequiredMode.REQUIRED)
        @Size(max = 300)
        String description
    ) {}

    @Schema(description = "관리자 계정 조회 요청 객체")
    public record FindBackOfficeUserRequest(
        @Schema(description = "관리자 계정 고유번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        List<@Positive Integer> userNoList,

        @Schema(description = "관리자 계정 아이디", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 20)
        String userId,

        @Schema(description = "관리자 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String userName,

        @Schema(description = "페이지 번호(0 이상의 정수, 예: 0)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @PositiveOrZero
        Integer page,

        @Schema(description = "페이지당 row의 개수(1 이상의 자연수, 예: 30)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Positive
        Integer size
    ) {}

    @Schema(description = "관리자 계정 수정 요청 객체")
    public record UpdateBackOfficeUserRequest(
        @Schema(description = "관리자 계정 아이디", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(min = 4, max = 100)
        String userId,

        @Schema(description = "관리자 이름", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Size(max = 20)
        String userName,

        @Schema(description = "새 비밀번호", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$",
                message = "비밀번호는 최소 8자리 이상이며, 대문자, 소문자, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다."
        )
        String newPassword,

        @Schema(description = "사용 여부", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
        String useYn,

        @Schema(description = "마지막 접속 IP", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @Pattern(
                regexp = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$",
                message = "IP 주소 형식이 올바르지 않습니다."
        )
        String lastAccessIp,

        @Schema(description = "마지막 접속 시각", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        LocalDateTime lastAccessDatetime,

        @Schema(description = "비고", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        String description,

        @Schema(description = "관리자 그룹 정보")
        List<BotUserGroup> userGroups
    ) {}

    @Schema(description = "관리자 계정 응답 객체")
    public record BackOfficeUserResponse(
        Integer userNo,
        String userId,
        String userName,
        BackOfficeGroupDto.BackOfficeGroupResponse botGroup,
        LocalDateTime registerDatetime,
        LocalDateTime modifyDatetime
    ) {}

    @Builder
    public record BackOfficeUserPageResponse(
        List<BackOfficeUserDto.BackOfficeUserResponse> backOfficeUserList,
        Long totalElements,
        Integer totalPages
    ) {}
}