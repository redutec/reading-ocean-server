package com.redutec.admin.backoffice.dto;

import com.redutec.core.criteria.BotUserCriteria;
import com.redutec.core.entity.BotUser;
import com.redutec.core.entity.BotUserGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class BackOfficeUserDto {
    @Schema(description = "관리자 계정 등록 요청 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    public static class CreateBackOfficeUserRequest {
        @Schema(description = "관리자 계정 아이디")
        @Size(min = 4, max = 100)
        private String userId;

        @Schema(description = "관리자 이름")
        @Size(max = 20)
        private String userName;

        @Schema(description = "비밀번호")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$",
                message = "비밀번호는 최소 8자리 이상이며, 대문자, 소문자, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다."
        )
        private String password;

        @Schema(description = "관리자 그룹 고유번호")
        @Positive
        private Integer groupNo;

        @Schema(description = "비고")
        @Size(max = 300)
        private String description;
    }

    @Schema(description = "관리자 계정 조회 요청 객체")
    @Builder
    @Getter
    @AllArgsConstructor
    public static class FindBackOfficeUserRequest {
        @Schema(description = "관리자 계정 고유번호")
        private List<@Positive Integer> userNoList;

        @Schema(description = "관리자 계정 아이디")
        @Size(max = 20)
        private String userId;

        @Schema(description = "관리자 이름")
        private String userName;

        @Schema(description = "페이지 번호 (0 이상의 정수, 예: 0)")
        @PositiveOrZero
        private Integer page;

        @Schema(description = "페이지당 row의 개수 (1 이상의 자연수, 예: 30)")
        @Positive
        private Integer size;

        public BotUserCriteria toCriteria() {
            return BotUserCriteria.builder()
                    .userNoList(this.userNoList)
                    .userId(this.userId)
                    .userName(this.userName)
                    .build();
        }
    }

    @Schema(description = "관리자 계정 수정 요청 객체")
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    public static class UpdateBackOfficeUserRequest {
        @Schema(description = "관리자 계정 아이디")
        @Size(min = 4, max = 100)
        private String userId;

        @Schema(description = "관리자 이름")
        @Size(max = 20)
        private String userName;

        @Schema(description = "새 비밀번호")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$",
                message = "비밀번호는 최소 8자리 이상이며, 대문자, 소문자, 숫자, 특수문자를 각각 1개 이상 포함해야 합니다."
        )
        private String newPassword;

        @Schema(description = "사용 여부")
        @Pattern(regexp = "^[YN]$", message = "사용 여부는 'Y' 또는 'N'이어야 합니다.")
        private String useYn;

        @Schema(description = "마지막 접속 IP")
        @Pattern(
                regexp = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$",
                message = "IP 주소 형식이 올바르지 않습니다."
        )
        private String lastAccessIp;

        private LocalDateTime lastAccessDatetime;

        @Schema(description = "비고")
        private String description;

        private List<BotUserGroup> userGroups;
    }

    @Schema(description = "관리자 계정 응답 객체")
    @Getter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class BackOfficeUserResponse {
        private Integer userNo;
        private String userId;
        private String userName;
        private BackOfficeGroupDto.BackOfficeGroupResponse botGroup;
        private LocalDateTime registerDatetime;
        private LocalDateTime modifyDatetime;

        public static BackOfficeUserResponse fromEntity(BotUser botUser) {
            BackOfficeGroupDto.BackOfficeGroupResponse botGroupResponse = Optional.ofNullable(botUser.getUserGroups())
                    .filter(list -> !list.isEmpty())
                    .map(list -> BackOfficeGroupDto.BackOfficeGroupResponse.fromEntity(list.getFirst().getGroup()))
                    .orElse(null);
            return BackOfficeUserResponse.builder()
                    .userNo(botUser.getUserNo())
                    .userId(botUser.getUserId())
                    .userName(botUser.getUserName())
                    .botGroup(botGroupResponse)
                    .registerDatetime(botUser.getRegisterDatetime())
                    .modifyDatetime(botUser.getModifyDatetime())
                    .build();
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BackOfficeUserPageResponse {
        private List<BackOfficeUserDto.BackOfficeUserResponse> backOfficeUserList;
        private long totalElements;
        private int totalPages;
    }
}