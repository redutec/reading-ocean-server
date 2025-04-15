package com.redutec.admin.backoffice.service;

import com.redutec.admin.backoffice.dto.BackOfficeGroupDto;
import com.redutec.admin.backoffice.mapper.BackOfficeGroupMapper;
import com.redutec.admin.config.JwtUtil;
import com.redutec.core.entity.v1.BotGroup;
import com.redutec.core.entity.v1.BotGroupPermission;
import com.redutec.core.entity.v1.BotMenu;
import com.redutec.core.entity.v1.key.BotGroupPermissionKey;
import com.redutec.core.repository.v1.BotGroupRepository;
import com.redutec.core.repository.v1.BotMenuRepository;
import com.redutec.core.specification.BotGroupSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BackOfficeGroupServiceImpl implements BackOfficeGroupService {
    private final BackOfficeGroupMapper backOfficeGroupMapper;
    private final BotGroupRepository botGroupRepository;
    private final BotMenuRepository botMenuRepository;
    private final JwtUtil jwtUtil;

    /**
     * 관리자 그룹 등록
     * @param createBackOfficeGroupRequest 관리자 그룹 등록 정보를 담은 DTO
     * @return 등록된 관리자 그룹 정보
     */
    @Override
    @Transactional
    public BackOfficeGroupDto.BackOfficeGroupResponse create(
            BackOfficeGroupDto.CreateBackOfficeGroupRequest createBackOfficeGroupRequest
    ) {
        return backOfficeGroupMapper.toResponse(botGroupRepository.save(backOfficeGroupMapper.toEntity(createBackOfficeGroupRequest)));
    }

    /**
     * 조건에 맞는 관리자 그룹 목록 조회
     * @param findBackOfficeGroupRequest 조회 조건을 담은 DTO
     * @return 조건에 맞는 관리자 그룹 목록
     */
    @Override
    @Transactional(readOnly = true)
    public BackOfficeGroupDto.BackOfficeGroupPageResponse find(
            BackOfficeGroupDto.FindBackOfficeGroupRequest findBackOfficeGroupRequest
    ) {
        // 조건에 맞는 관리자 그룹 조회
        Page<BotGroup> botGroupPage = botGroupRepository.findAll(
                BotGroupSpecification.findWith(backOfficeGroupMapper.toCriteria(findBackOfficeGroupRequest)),
                (findBackOfficeGroupRequest.page() != null && findBackOfficeGroupRequest.size() != null)
                        ? PageRequest.of(findBackOfficeGroupRequest.page(), findBackOfficeGroupRequest.size())
                        : Pageable.unpaged());
        // 각 BotGroup에 포함된 관리자 계정들을 userGroups에 담기, 그 안의 BotGroup.userGroups를 강제로 초기화 (필요시)
        botGroupPage.getContent().forEach(botGroup ->
            botGroup.getUserGroups().forEach(botUserGroup ->
                    Hibernate.initialize(botUserGroup.getGroup().getUserGroups())
            )
        );
        return backOfficeGroupMapper.toPageResponseDto(botGroupPage);
    }

    @Override
    @Transactional(readOnly = true)
    public BackOfficeGroupDto.BackOfficeGroupWithPermissionResponse findByGroupNo(
            Integer groupNo
    ) {
        BotGroup botGroup = getBackOfficeGroup(groupNo);
        // 지연 로딩된 컬렉션 초기화
        Hibernate.initialize(botGroup.getUserGroups());
        Hibernate.initialize(botGroup.getGroupPermissions());
        // 특정 관리자 그룹 리턴
        return backOfficeGroupMapper.toResponseWithPermission(botGroup);
    }

    /**
     * 특정 관리자 그룹 조회
     * @param groupNo 관리자 그룹 고유번호
     * @return 특정 관리자 그룹 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public BotGroup getBackOfficeGroup(
            Integer groupNo
    ) {
        return botGroupRepository.findById(Long.valueOf(groupNo)).orElseThrow(() -> new EntityNotFoundException("No such BotGroup"));
    }

    /**
     * 관리자 그룹 수정
     * @param groupNo 수정할 관리자 그룹의 고유번호
     * @param updateBackOfficeGroupRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Integer groupNo,
            BackOfficeGroupDto.UpdateBackOfficeGroupRequest updateBackOfficeGroupRequest
    ) {
        // 수정할 관리자 그룹 조회
        BotGroup botGroup = getBackOfficeGroup(groupNo);
        // 기본 필드 업데이트
        botGroup.updateBotGroup(
                updateBackOfficeGroupRequest.groupName(),
                updateBackOfficeGroupRequest.useYn(),
                Optional.ofNullable(jwtUtil.extractUserIdFromToken((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal()))
                        .orElse("admin"),
                updateBackOfficeGroupRequest.description()
        );
        // 그룹 권한 요청 목록이 전달되었다면 기존 권한을 대체합니다.
        if (updateBackOfficeGroupRequest.groupPermissions() != null) {
            // 기존 권한 모두 삭제 (orphanRemoval=true이므로 자동 삭제)
            botGroup.getGroupPermissions().clear();
            // 전달받은 요청 목록을 순회하며 새 권한 생성 후 추가
            updateBackOfficeGroupRequest.groupPermissions().forEach(permissionRequest -> {
                // BotGroupPermissionKey 생성
                BotGroupPermissionKey key = new BotGroupPermissionKey(botGroup.getGroupNo(), Math.toIntExact(permissionRequest.menuNo()));
                // BotMenu 엔티티 조회 (menuNo로 조회)
                BotMenu botMenu = botMenuRepository.findById(Math.toIntExact(permissionRequest.menuNo()))
                        .orElseThrow(() -> new EntityNotFoundException("No such BotMenu"));
                // 새 BotGroupPermission 엔티티 생성 (BotMenu 엔티티 조회는 필요한 경우 추가)
                BotGroupPermission newPermission = BotGroupPermission.builder()
                        .id(key)
                        .group(botGroup)
                        .menu(botMenu)
                        .permissionType(permissionRequest.permissionType())
                        .useYn(permissionRequest.useYn())
                        .build();
                // 생성된 권한 엔티티를 그룹의 권한 리스트에 추가
                botGroup.getGroupPermissions().add(newPermission);
            });
        }
        // 변경된 엔티티 저장 (cascade 옵션에 따라 권한 엔티티도 저장됩니다)
        botGroupRepository.save(botGroup);
    }

    /**
     * 관리자 그룹 삭제
     * @param groupNo 삭제할 관리자 그룹의 고유번호
     */
    @Override
    @Transactional
    public void delete(
            Integer groupNo
    ) {
        botGroupRepository.delete(getBackOfficeGroup(groupNo));
    }
}