package com.redutec.admin.bot.dto.response;

import com.redutec.core.entity.BotUser;
import com.redutec.core.entity.BotUserGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Schema(defaultValue = "계정 조회 응답 객체")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BotUserResponse {
    private Integer userNo;
    private String userId;
    private String userName;
    private String registerDatetime;
    private String modifyDatetime;
    private List<Integer> groupNos;
    private List<String> groupNames;

    public static BotUserResponse fromEntity(BotUser botUser) {
        List<Integer> groupNos = botUser.getUserGroups().stream()
                .map(botUserGroup -> botUserGroup.getGroup().getGroupNo())
                .collect(Collectors.toList());
        List<String> groupNames = botUser.getUserGroups().stream()
                .map(botUserGroup -> botUserGroup.getGroup().getGroupName())
                .collect(Collectors.toList());
        return BotUserResponse.builder()
                .userNo(botUser.getUserNo())
                .userId(botUser.getUserId())
                .userName(botUser.getUserName())
                .registerDatetime(botUser.getRegisterDatetime().toString())
                .modifyDatetime(botUser.getModifyDatetime().toString())
                .groupNos(groupNos)
                .groupNames(groupNames)
                .build();
    }
}