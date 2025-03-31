package com.redutec.admin.bot.dto;

import com.redutec.core.criteria.BotGroupCriteria;
import com.redutec.core.entity.BotGroup;
import com.redutec.core.entity.BotGroupPermission;
import com.redutec.core.meta.PermissionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BotGroupDto {
    /**
     * 관리자 그룹 등록 요청 DTO
     */
    @Schema(description = "관리자 그룹 등록 요청 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateBotGroupRequest {
        @Schema(description = "관리자 그룹 이름", example = "테스트 관리자 그룹")
        @NotBlank
        @Size(max = 100)
        private String groupName;

        @Schema(description = "비고", example = "테스트")
        @Size(max = 300)
        private String description;

        @Schema(description = "사용 여부", example = "N")
        @NotBlank
        @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
        private String useYn;
    }

    /**
     * 관리자 그룹 조회 요청 DTO
     */
    @Schema(description = "관리자 그룹 조회 요청 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindBotGroupRequest {
        @Schema(description = "관리자 그룹 고유번호 목록")
        private List<Long> groupNoList;

        @Schema(description = "관리자 그룹 이름")
        private String groupName;

        @Schema(description = "페이지 번호 (0 이상의 정수)")
        @PositiveOrZero
        private Integer page;

        @Schema(description = "페이지당 row의 개수 (1 이상의 자연수)")
        @PositiveOrZero
        private Integer size;

        public BotGroupCriteria toCriteria() {
            return BotGroupCriteria.builder()
                    .groupNoList(this.groupNoList)
                    .groupName(this.groupName)
                    .build();
        }
    }

    @Schema(description = "관리자 그룹 수정 요청 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateBotGroupRequest {
        @Schema(description = "관리자 그룹 이름", example = "테스트 관리자 그룹")
        @NotBlank
        @Size(max = 100)
        private String groupName;

        @Schema(description = "비고", example = "테스트")
        @Size(max = 300)
        private String description;

        @Schema(description = "사용 여부", example = "Y")
        @NotBlank
        @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
        private String useYn;

        @Schema(description = "그룹 권한 요청 목록")
        private List<BotGroupPermissionRequest> groupPermissions;

        @Schema(description = "그룹 권한 요청 객체 (업데이트 시 사용)")
        @Builder
        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class BotGroupPermissionRequest {
            @Schema(description = "메뉴 번호", example = "1001")
            private Long menuNo;

            @Schema(description = "권한 타입", example = "PMT001")
            @NotBlank
            private PermissionType permissionType;

            @Schema(description = "사용 여부", example = "Y")
            @NotBlank
            @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
            private String useYn;
        }
    }

    /**
     * 관리자 그룹 권한 응답 DTO
     */
    @Schema(description = "관리자 그룹 권한 응답 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BotGroupPermissionResponse {
        @Schema(description = "메뉴 번호")
        private Long menuNo;

        @Schema(description = "관리자 그룹 고유번호")
        private Integer groupNo;

        @Schema(description = "권한 타입")
        private PermissionType permissionType;

        @Schema(description = "등록 일시")
        private LocalDateTime registerDatetime;

        @Schema(description = "수정 일시")
        private LocalDateTime modifyDatetime;

        public static BotGroupPermissionResponse fromEntity(BotGroupPermission botGroupPermission) {
            return BotGroupPermissionResponse.builder()
                    .menuNo(Long.valueOf(botGroupPermission.getId().getMenuNo()))
                    .groupNo(botGroupPermission.getId().getGroupNo())
                    .permissionType(botGroupPermission.getPermissionType())
                    .registerDatetime(botGroupPermission.getRegisterDatetime())
                    .modifyDatetime(botGroupPermission.getModifyDatetime())
                    .build();
        }
    }

    /**
     * 관리자 그룹 응답 DTO
     */
    @Schema(description = "관리자 그룹 응답 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BotGroupResponse {
        @Schema(description = "관리자 그룹 고유번호")
        private Integer groupNo;

        @Schema(description = "관리자 그룹 이름")
        private String groupName;

        @Schema(description = "사용 여부")
        private String useYn;

        @Schema(description = "등록 일시")
        private LocalDateTime registerDatetime;

        @Schema(description = "수정 일시")
        private LocalDateTime modifyDatetime;

        @Schema(description = "비고")
        private String description;

        @Schema(description = "해당 그룹에 속한 관리자 계정 고유번호 목록")
        private List<Integer> userGroups;

        public static BotGroupResponse fromEntity(BotGroup botGroup) {
            List<Integer> userGroups = Optional.ofNullable(botGroup.getUserGroups())
                    .filter(Hibernate::isInitialized)
                    .map(list -> list.stream()
                            .map(botUserGroup -> botUserGroup.getUser().getUserNo())
                            .collect(Collectors.toList()))
                    .orElse(Collections.emptyList());
            return BotGroupResponse.builder()
                    .groupNo(botGroup.getGroupNo())
                    .groupName(botGroup.getGroupName())
                    .useYn(botGroup.getUseYn())
                    .registerDatetime(botGroup.getRegisterDatetime())
                    .modifyDatetime(botGroup.getModifyDatetime())
                    .description(botGroup.getDescription())
                    .userGroups(userGroups)
                    .build();
        }
    }

    /**
     * 관리자 그룹 응답 DTO(접근권한 포함)
     */
    @Schema(description = "관리자 그룹 응답 객체(접근 권한 포함)")
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BotGroupWithPermissionResponse {
        @Schema(description = "관리자 그룹 고유번호")
        private Integer groupNo;

        @Schema(description = "관리자 그룹 이름")
        private String groupName;

        @Schema(description = "사용 여부")
        private String useYn;

        @Schema(description = "등록 일시")
        private LocalDateTime registerDatetime;

        @Schema(description = "수정 일시")
        private LocalDateTime modifyDatetime;

        @Schema(description = "비고")
        private String description;

        @Schema(description = "해당 그룹에 속한 관리자 계정 고유번호 목록")
        private List<Integer> userGroups;

        @Schema(description = "해당 그룹에 속한 그룹 권한 목록")
        private List<BotGroupPermissionResponse> groupPermissions;

        public static BotGroupWithPermissionResponse fromEntity(BotGroup botGroup) {
            List<Integer> userGroups = Optional.ofNullable(botGroup.getUserGroups())
                    .filter(Hibernate::isInitialized)
                    .map(list -> list.stream()
                            .map(botUserGroup -> botUserGroup.getUser().getUserNo())
                            .collect(Collectors.toList()))
                    .orElse(Collections.emptyList());
            List<BotGroupPermissionResponse> groupPermissions = Optional.ofNullable(botGroup.getGroupPermissions())
                    .filter(Hibernate::isInitialized)
                    .map(list -> list.stream()
                            .map(BotGroupPermissionResponse::fromEntity)
                            .collect(Collectors.toList()))
                    .orElse(Collections.emptyList());
            return BotGroupWithPermissionResponse.builder()
                    .groupNo(botGroup.getGroupNo())
                    .groupName(botGroup.getGroupName())
                    .useYn(botGroup.getUseYn())
                    .registerDatetime(botGroup.getRegisterDatetime())
                    .modifyDatetime(botGroup.getModifyDatetime())
                    .description(botGroup.getDescription())
                    .userGroups(userGroups)
                    .groupPermissions(groupPermissions)
                    .build();
        }
    }

    /**
     * 관리자 그룹 목록 및 페이징 정보를 포함하는 응답 DTO
     */
    @Schema(description = "관리자 그룹 목록 및 페이징 정보 응답 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BotGroupPageResponse {
        @Schema(description = "관리자 그룹 목록")
        private List<BotGroupResponse> botGroupList;

        @Schema(description = "전체 요소 수")
        private long totalElements;

        @Schema(description = "전체 페이지 수")
        private int totalPages;
    }
}