package com.redutec.core.mapper;

import com.redutec.core.criteria.AdminUserCriteria;
import com.redutec.core.dto.AdminUserDto;
import com.redutec.core.entity.AdminMenu;
import com.redutec.core.entity.AdminUser;
import com.redutec.core.repository.AdminMenuRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.BadCredentialsException;
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
     * @return 등록할 AdminUser 엔티티
     */
    public AdminUser createEntity(AdminUserDto.CreateAdminUserRequest createAdminUserRequest) {
        return AdminUser.builder()
                .accountId(StringUtils.stripToNull(createAdminUserRequest.accountId()))
                .password(passwordEncoder.encode(StringUtils.stripToNull(createAdminUserRequest.password())))
                .nickname(StringUtils.stripToNull(createAdminUserRequest.nickname()))
                .email(StringUtils.stripToNull(createAdminUserRequest.email()))
                .phoneNumber(StringUtils.stripToNull(createAdminUserRequest.phoneNumber()))
                .role(createAdminUserRequest.role())
                .authenticationStatus(createAdminUserRequest.authenticationStatus())
                .failedLoginAttempts(0)
                .build();
    }

    /**
     * UpdateAdminUserRequest DTO를 기반으로 AdminUser 엔티티를 수정합니다.
     *
     * @param adminUser 수정할 어드민 사용자 엔티티
     * @param updateAdminUserRequest 어드민 사용자 수정에 필요한 데이터를 담은 DTO
     */
    public void updateEntity(
            AdminUser adminUser,
            AdminUserDto.UpdateAdminUserRequest updateAdminUserRequest
    ) {
        Optional.ofNullable(StringUtils.stripToNull(updateAdminUserRequest.accountId()))
                .ifPresent(adminUser::setAccountId);
        Optional.ofNullable(StringUtils.stripToNull(updateAdminUserRequest.newPassword()))
                .ifPresent(newPassword -> {
                    // currentPassword 미입력 시 예외
                    Optional.ofNullable(StringUtils.stripToNull(updateAdminUserRequest.currentPassword()))
                            .orElseThrow(() -> new BadCredentialsException("비밀번호를 변경하려면 현재 비밀번호를 입력해야 합니다."));
                    // currentPassword 불일치 시 예외
                    Optional.of(StringUtils.stripToNull(updateAdminUserRequest.currentPassword()))
                            .filter(currentPassword -> passwordEncoder.matches(currentPassword, adminUser.getPassword()))
                            .orElseThrow(() -> new BadCredentialsException("현재 비밀번호가 일치하지 않습니다."));
                    // 새 비밀번호 암호화 후 Set
                    adminUser.setPassword(passwordEncoder.encode(newPassword));
                });
        Optional.ofNullable(StringUtils.stripToNull(updateAdminUserRequest.nickname()))
                .ifPresent(adminUser::setNickname);
        Optional.ofNullable(StringUtils.stripToNull(updateAdminUserRequest.email()))
                .ifPresent(adminUser::setEmail);
        Optional.ofNullable(StringUtils.stripToNull(updateAdminUserRequest.phoneNumber()))
                .ifPresent(adminUser::setPhoneNumber);
        Optional.ofNullable(updateAdminUserRequest.role())
                .ifPresent(adminUser::setRole);
        Optional.ofNullable(updateAdminUserRequest.authenticationStatus())
                .ifPresent(adminUser::setAuthenticationStatus);
        Optional.ofNullable(updateAdminUserRequest.failedLoginAttempts())
                .ifPresent(adminUser::setFailedLoginAttempts);
        Optional.ofNullable(updateAdminUserRequest.lastLoginIp())
                .ifPresent(adminUser::setLastLoginIp);
        Optional.ofNullable(updateAdminUserRequest.lastLoginAt())
                .ifPresent(adminUser::setLastLoginAt);
    }
    
    /**
     * 이 메서드는 현재 FindAdminUserRequest 객체를 기반으로
     * AdminUserCriteria 객체를 생성합니다.
     * 내부 검색 로직에서 어드민 사용자 검색 조건을 구성할 때 사용됩니다.
     *
     * @param findAdminUserRequest 어드민 사용자 조회 요청 객체
     * @return 해당 요청의 필드를 이용해 생성된 AdminUserCriteria 객체
     */
    public AdminUserCriteria toCriteria(AdminUserDto.FindAdminUserRequest findAdminUserRequest) {
        return new AdminUserCriteria(
                findAdminUserRequest.adminUserIds(),
                StringUtils.stripToNull(findAdminUserRequest.accountId()),
                StringUtils.stripToNull(findAdminUserRequest.nickname()),
                StringUtils.stripToNull(findAdminUserRequest.email()),
                findAdminUserRequest.roles(),
                findAdminUserRequest.authenticationStatuses()
        );
    }

    /**
     * AdminUser 엔티티를 기반으로 응답용 AdminUserResponse DTO로 변환합니다.
     * Optional을 사용하여 null 검사를 수행합니다.
     *
     * @param adminUser 변환할 AdminUser 엔티티
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
     * @param adminUserPage Page 형태로 조회된 AdminUser 엔티티 목록
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