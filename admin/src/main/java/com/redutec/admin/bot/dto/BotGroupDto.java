package com.redutec.admin.bot.dto;

import com.redutec.core.criteria.BotGroupCriteria;
import com.redutec.core.entity.BotGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
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
    public static class CreateBotGroup {
        @Schema(description = "관리자 그룹 이름")
        @NotBlank
        @Size(max = 100)
        private String groupName;

        @Schema(description = "비고")
        @Size(max = 300)
        private String description;
    }

    /**
     * 관리자 그룹 조회 요청 DTO
     */
    @Schema(description = "관리자 그룹 조회 요청 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindBotGroup {
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

    /**
     * 관리자 그룹 수정 요청 DTO
     */
    @Schema(description = "관리자 그룹 수정 요청 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateBotGroup {
        @Schema(description = "관리자 그룹 이름")
        @NotBlank
        @Size(max = 100)
        private String groupName;

        @Schema(description = "비고")
        @Size(max = 300)
        private String description;

        @Schema(description = "사용 여부", example = "Y")
        @NotBlank
        @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
        private String useYn;
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
        private List<Integer> userNoList;

        public static BotGroupResponse fromEntity(BotGroup botGroup) {
            List<Integer> userNoList = botGroup.getUserGroups() != null
                    ? botGroup.getUserGroups().stream()
                    .map(botUserGroup -> botUserGroup.getUser().getUserNo())
                    .collect(Collectors.toList())
                    : null;
            return BotGroupResponse.builder()
                    .groupNo(botGroup.getGroupNo())
                    .groupName(botGroup.getGroupName())
                    .useYn(botGroup.getUseYn())
                    .registerDatetime(botGroup.getRegisterDatetime())
                    .modifyDatetime(botGroup.getModifyDatetime())
                    .description(botGroup.getDescription())
                    .userNoList(userNoList)
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