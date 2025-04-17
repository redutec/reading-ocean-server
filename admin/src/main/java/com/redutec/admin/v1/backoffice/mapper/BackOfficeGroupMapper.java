package com.redutec.admin.v1.backoffice.mapper;

import com.redutec.admin.v1.backoffice.dto.BackOfficeGroupDto;
import com.redutec.admin.config.JwtUtil;
import com.redutec.core.criteria.v1.BotGroupCriteria;
import com.redutec.core.entity.v1.BotGroup;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class BackOfficeGroupMapper {
    private final JwtUtil jwtUtil;

    /**
     * CreateBackOfficeGroupRequest DTO를 기반으로 BotGroup 엔티티를 생성합니다.
     * @param createBackOfficeGroupRequest 사이트 설정 생성에 필요한 데이터를 담은 DTO
     * @return 생성된 BotGroup 엔티티
     */
    public BotGroup toEntity(
            BackOfficeGroupDto.CreateBackOfficeGroupRequest createBackOfficeGroupRequest
    ) {
        String adminId = Optional.ofNullable(jwtUtil.extractUsername((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                .orElse("admin");
        return BotGroup.builder()
                .groupName(createBackOfficeGroupRequest.groupName())
                .useYn(createBackOfficeGroupRequest.useYn())
                .adminId(adminId)
                .description(createBackOfficeGroupRequest.description())
                .build();
    }

    /**
     * FindBackOfficeGroupRequest DTO를 기반으로 검색 조건 객체인 BotGroupCriteria를 생성합니다.
     * 내부 검색 로직에서 사이트 설정 검색 조건을 구성할 때 사용됩니다.
     * @param findBackOfficeGroupRequest 사이트 설정 조회 조건을 담은 DTO
     * @return 해당 요청의 필드를 이용해 생성된 BotGroupCriteria 객체
     */
    public BotGroupCriteria toCriteria(
            BackOfficeGroupDto.FindBackOfficeGroupRequest findBackOfficeGroupRequest
    ) {
        return BotGroupCriteria.builder()
                .groupNoList(findBackOfficeGroupRequest.groupNoList())
                .groupName(findBackOfficeGroupRequest.groupName())
                .build();
    }

    /**
     * BotGroup 엔티티를 기반으로 응답용 BackOfficeGroupResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     * @param botGroup 변환할 엔티티 (null 가능)
     * @return BotGroup 엔티티의 데이터를 담은 BackOfficeGroupResponse DTO, company가 null이면 null 반환
     */
    public BackOfficeGroupDto.BackOfficeGroupResponse toResponse(
            BotGroup botGroup
    ) {
        List<Integer> userGroups = Optional.ofNullable(botGroup.getUserGroups())
                .filter(Hibernate::isInitialized)
                .map(list -> list.stream()
                        .map(botUserGroup -> botUserGroup.getUser().getUserNo())
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
        return Optional.of(botGroup)
                .map(b -> new BackOfficeGroupDto.BackOfficeGroupResponse(
                        b.getGroupNo(),
                        b.getGroupName(),
                        b.getUseYn(),
                        b.getRegisterDatetime(),
                        b.getModifyDatetime(),
                        b.getDescription(),
                        userGroups
                ))
                .orElse(null);
    }

    public BackOfficeGroupDto.BackOfficeGroupWithPermissionResponse toResponseWithPermission(
            BotGroup botGroup
    ) {
        List<Integer> userGroups = Optional.ofNullable(botGroup.getUserGroups())
                .filter(Hibernate::isInitialized)
                .map(list -> list.stream()
                        .map(botUserGroup -> botUserGroup.getUser().getUserNo())
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
        List<BackOfficeGroupDto.BackOfficeGroupPermissionResponse> groupPermissions = Optional.ofNullable(botGroup.getGroupPermissions())
                .filter(Hibernate::isInitialized)
                .map(list -> list.stream()
                        .map(bp -> new BackOfficeGroupDto.BackOfficeGroupPermissionResponse(
                                bp.getMenu().getMenuNo(),
                                botGroup.getGroupNo(),
                                bp.getPermissionType(),
                                bp.getRegisterDatetime(),
                                bp.getModifyDatetime()
                        ))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
        return new BackOfficeGroupDto.BackOfficeGroupWithPermissionResponse(
                botGroup.getGroupNo(),
                botGroup.getGroupName(),
                botGroup.getUseYn(),
                botGroup.getRegisterDatetime(),
                botGroup.getModifyDatetime(),
                botGroup.getDescription(),
                userGroups,
                groupPermissions
        );
    }


    /**
     * Page 형식의 엔티티 목록을 BackOfficeGroupPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param botGroupPage Page 형태로 조회된 엔티티 목록 (null 가능)
     * @return BotGroup 엔티티 리스트와 페이지 정보를 담은 BackOfficeGroupPageResponse DTO, botGroupPage가 null이면 null 반환
     */
    public BackOfficeGroupDto.BackOfficeGroupPageResponse toPageResponseDto(
            Page<BotGroup> botGroupPage
    ) {
        return Optional.ofNullable(botGroupPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponse)
                            .collect(Collectors.toList());
                    return new BackOfficeGroupDto.BackOfficeGroupPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}