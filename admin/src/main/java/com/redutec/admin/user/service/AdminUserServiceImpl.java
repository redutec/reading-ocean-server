package com.redutec.admin.user.service;

import com.redutec.core.dto.AdminUserDto;
import com.redutec.core.entity.AdminUser;
import com.redutec.core.mapper.AdminUserMapper;
import com.redutec.core.repository.AdminUserRepository;
import com.redutec.core.specification.AdminUserSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final AdminUserMapper adminUserMapper;
    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 어드민 사용자 등록
     * @param createAdminUserRequest 어드민 사용자 등록 정보를 담은 DTO
     * @return 등록된 어드민 사용자 정보
     */
    @Override
    @Transactional
    public AdminUserDto.AdminUserResponse create(AdminUserDto.CreateAdminUserRequest createAdminUserRequest) {
        return adminUserMapper.toResponseDto(adminUserRepository.save(adminUserMapper.createEntity(createAdminUserRequest)));
    }

    /**
     * 조건에 맞는 어드민 사용자 목록 조회
     * @param findAdminUserRequest 조회 조건을 담은 DTO
     * @return 조회된 어드민 사용자 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public AdminUserDto.AdminUserPageResponse find(AdminUserDto.FindAdminUserRequest findAdminUserRequest) {
        return adminUserMapper.toPageResponseDto(adminUserRepository.findAll(
                AdminUserSpecification.findWith(adminUserMapper.toCriteria(findAdminUserRequest)),
                (findAdminUserRequest.page() != null && findAdminUserRequest.size() != null)
                        ? PageRequest.of(findAdminUserRequest.page(), findAdminUserRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 어드민 사용자 조회
     * @param adminUserId 어드민 사용자 고유번호
     * @return 특정 어드민 사용자 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public AdminUserDto.AdminUserResponse findById(Long adminUserId) {
        return adminUserMapper.toResponseDto(getAdminUser(adminUserId));
    }

    /**
     * 어드민 사용자 수정
     * @param adminUserId 수정할 어드민 사용자의 ID
     * @param updateAdminUserRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(Long adminUserId, AdminUserDto.UpdateAdminUserRequest updateAdminUserRequest) {
        // 수정할 어드민 사용자 엔티티 조회
        AdminUser adminUser = getAdminUser(adminUserId);
        // 수정 요청 객체에 newPassword가 존재한다면 현재 비밀번호와 currentPassword가 일치하는지 검증
        Optional.ofNullable(updateAdminUserRequest.newPassword())
                .filter(newPassword -> !newPassword.isBlank())
                .ifPresent(newPassword -> {
                    // currentPassword 미입력 시 예외
                    Optional.ofNullable(updateAdminUserRequest.currentPassword())
                            .filter(currentPassword -> !currentPassword.isBlank())
                            .orElseThrow(() -> new IllegalArgumentException("비밀번호를 변경하려면 현재 비밀번호를 입력해야 합니다."));
                    // currentPassword 불일치 시 예외
                    Optional.of(updateAdminUserRequest.currentPassword())
                            .filter(currentPassword -> passwordEncoder.matches(currentPassword, adminUser.getPassword()))
                            .orElseThrow(() -> new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다."));
                });
        // 어드민 사용자 수정 엔티티 빌드 후 UPDATE
        adminUserMapper.updateEntity(adminUser, updateAdminUserRequest);
    }

    /**
     * 어드민 사용자 삭제
     * @param adminUserId 삭제할 어드민 사용자의 ID
     */
    @Override
    @Transactional
    public void delete(Long adminUserId) {
        adminUserRepository.delete(getAdminUser(adminUserId));
    }


    /**
     * 특정 어드민 사용자 엔티티 조회
     * @param adminUserId 어드민 사용자 고유번호
     * @return 특정 어드민 사용자 엔티티 객체
     */
    @Transactional(readOnly = true)
    public AdminUser getAdminUser(Long adminUserId) {
        return adminUserRepository.findById(adminUserId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 어드민 사용자입니다. adminUserId: " + adminUserId));
    }
}