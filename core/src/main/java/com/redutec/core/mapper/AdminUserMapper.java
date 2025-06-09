package com.redutec.core.mapper;

import com.redutec.core.criteria.AdminUserCriteria;
import com.redutec.core.dto.AdminUserDto;
import com.redutec.core.entity.AdminMenu;
import com.redutec.core.entity.AdminUser;
import com.redutec.core.repository.AdminMenuRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@Component
public class AdminUserMapper {
    private final PasswordEncoder passwordEncoder;
    private final AdminMenuRepository adminMenuRepository;

    /**
     * CreateAdminUserRequest DTO를 기반으로 AdminUser 등록 엔티티를 생성합니다.
     *
     * @param createAdminUserRequest 어드민 사용자 등록에 필요한 데이터를 담은 DTO
     * @return 생성된 AdminUser 등록 엔티티
     */
    public AdminUser toCreateEntity(AdminUserDto.CreateAdminUserRequest createAdminUserRequest) {
        return AdminUser.builder()
                .accountId(createAdminUserRequest.accountId())
                .password(passwordEncoder.encode(createAdminUserRequest.password()))
                .nickname(createAdminUserRequest.nickname())
                .email(createAdminUserRequest.email())
                .phoneNumber(createAdminUserRequest.phoneNumber())
                .role(createAdminUserRequest.role())
                .authenticationStatus(createAdminUserRequest.authenticationStatus())
                .failedLoginAttempts(0)
                .build();
    }

    /**
     * UpdateAdminUserRequest DTO를 기반으로 AdminUser 수정 엔티티를 생성합니다.
     *
     * @param updateAdminUserRequest 어드민 사용자 수정에 필요한 데이터를 담은 DTO
     * @return 생성된 AdminUser 수정 엔티티
     */
    public AdminUser toUpdateEntity(
            AdminUser adminUser,
            AdminUserDto.UpdateAdminUserRequest updateAdminUserRequest
    ) {
        return AdminUser.builder()
                .id(adminUser.getId())
                .accountId(Optional.ofNullable(updateAdminUserRequest.accountId()).orElse(adminUser.getAccountId()))
                .password(Optional.ofNullable(updateAdminUserRequest.newPassword())
                        .filter(newPassword -> !newPassword.isBlank())
                        .map(passwordEncoder::encode)
                        .orElse(adminUser.getPassword()))
                .nickname(Optional.ofNullable(updateAdminUserRequest.nickname()).orElse(adminUser.getNickname()))
                .email(Optional.ofNullable(updateAdminUserRequest.email()).orElse(adminUser.getEmail()))
                .phoneNumber(Optional.ofNullable(updateAdminUserRequest.phoneNumber()).orElse(adminUser.getPhoneNumber()))
                .role(Optional.ofNullable(updateAdminUserRequest.role()).orElse(adminUser.getRole()))
                .authenticationStatus(Optional.ofNullable(updateAdminUserRequest.authenticationStatus()).orElse(adminUser.getAuthenticationStatus()))
                .failedLoginAttempts(Optional.ofNullable(updateAdminUserRequest.failedLoginAttempts()).orElse(adminUser.getFailedLoginAttempts()))
                .lastLoginIp(Optional.ofNullable(updateAdminUserRequest.lastLoginIp()).orElse(adminUser.getLastLoginIp()))
                .lastLoginAt(Optional.ofNullable(updateAdminUserRequest.lastLoginAt()).orElse(adminUser.getLastLoginAt()))
                .build();
    }
    
    /**
     * 이 메서드는 현재 FindAdminUserRequest 객체를 기반으로
     * AdminUserCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 어드민 사용자 검색 조건을 구성할 때 사용됩니다.
     *
     * @return 해당 요청의 필드를 이용해 생성된 AdminUserCriteria 객체
     */
    public AdminUserCriteria toCriteria(AdminUserDto.FindAdminUserRequest findAdminUserRequest) {
        return new AdminUserCriteria(
                findAdminUserRequest.adminUserIds(),
                findAdminUserRequest.accountId(),
                findAdminUserRequest.nickname(),
                findAdminUserRequest.email(),
                findAdminUserRequest.roles(),
                findAdminUserRequest.authenticationStatuses()
        );
    }

    /**
     * AdminUser 엔티티를 기반으로 응답용 AdminUserResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param adminUser 변환할 AdminUser 엔티티 (null 가능)
     * @return AdminUser 엔티티의 데이터를 담은 AdminUserResponse DTO, adminUser가 null이면 null 반환
     */
    public AdminUserDto.AdminUserResponse toResponseDto(AdminUser adminUser) {
        return Optional.ofNullable(adminUser)
                .map(admin -> new AdminUserDto.AdminUserResponse(
                        admin.getId(),
                        admin.getAccountId(),
                        admin.getNickname(),
                        admin.getEmail(),
                        adminMenuRepository.findAllByAccessibleRolesContains(admin.getRole()).stream()
                                .map(AdminMenu::getId)
                                .collect(Collectors.toList()),
                        admin.getRole(),
                        admin.getAuthenticationStatus(),
                        admin.getFailedLoginAttempts(),
                        admin.getLastLoginIp(),
                        admin.getLastLoginAt(),
                        admin.getCreatedAt(),
                        admin.getUpdatedAt()
                ))
                .orElse(null);
    }

    /**
     * Page 형식의 AdminUser 엔티티 목록을 AdminUserPageResponse DTO로 변환합니다.
     * 엔티티 리스트를 응답용 DTO 리스트로 매핑하고 페이지네이션 정보를 포함합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param adminUserPage Page 형태로 조회된 AdminUser 엔티티 목록 (null 가능)
     * @return AdminUser 엔티티 리스트와 페이지 정보를 담은 AdminUserPageResponse DTO, adminUserPage가 null이면 null 반환
     */
    public AdminUserDto.AdminUserPageResponse toPageResponseDto(Page<AdminUser> adminUserPage) {
        return Optional.ofNullable(adminUserPage)
                .map(page -> {
                    var responseList = page.getContent().stream()
                            .map(this::toResponseDto)
                            .collect(Collectors.toList());
                    return new AdminUserDto.AdminUserPageResponse(
                            responseList,
                            page.getTotalElements(),
                            page.getTotalPages()
                    );
                })
                .orElse(null);
    }
}