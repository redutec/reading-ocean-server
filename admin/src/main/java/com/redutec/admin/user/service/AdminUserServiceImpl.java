package com.redutec.admin.user.service;

import com.redutec.admin.user.dto.AdminUserDto;
import com.redutec.admin.user.mapper.AdminUserMapper;
import com.redutec.admin.config.JwtUtil;
import com.redutec.core.entity.AdminUser;
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

@Service
@Slf4j
@AllArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final AdminUserMapper adminUserMapper;
    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 어드민 사용자 등록
     * @param createAdminUserRequest 어드민 사용자 등록 정보를 담은 DTO
     * @return 등록된 어드민 사용자 정보
     */
    @Override
    @Transactional
    public AdminUserDto.AdminUserResponse create(
            AdminUserDto.CreateAdminUserRequest createAdminUserRequest
    ) {
        return adminUserMapper.toResponseDto(
                adminUserRepository.save(
                        adminUserMapper.toEntity(
                                createAdminUserRequest
                        )
                )
        );
    }

    /**
     * 조건에 맞는 어드민 사용자 목록 조회
     * @param findAdminUserRequest 조회 조건을 담은 DTO
     * @return 조회된 어드민 사용자 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public AdminUserDto.AdminUserPageResponse find(
            AdminUserDto.FindAdminUserRequest findAdminUserRequest
    ) {
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
    public AdminUserDto.AdminUserResponse findById(
            Long adminUserId
    ) {
        return adminUserMapper.toResponseDto(getAdminUser(adminUserId));
    }

    /**
     * 특정 어드민 사용자 엔티티 조회(닉네임으로 조회)
     * @param email 어드민 사용자 닉네임
     * @return 특정 어드민 사용자 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public AdminUser findByEmail(
            String email
    ) {
        return adminUserRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("No such adminUser"));
    }

    /**
     * 특정 어드민 사용자 엔티티 조회
     * @param adminUserId 어드민 사용자 고유번호
     * @return 특정 어드민 사용자 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public AdminUser getAdminUser(
            Long adminUserId
    ) {
        return adminUserRepository.findById(adminUserId)
                .orElseThrow(() -> new EntityNotFoundException("No such adminUser"));
    }

    /**
     * 어드민 사용자 수정
     * @param adminUserId 수정할 어드민 사용자의 ID
     * @param updateAdminUserRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Long adminUserId,
            AdminUserDto.UpdateAdminUserRequest updateAdminUserRequest
    ) {
        // 수정할 어드민 사용자 엔티티 조회
        AdminUser adminUser = getAdminUser(adminUserId);
        // UPDATE 도메인 메서드로 변환
        adminUser.updateAdminUser(
                updateAdminUserRequest.email(),
                passwordEncoder.encode(updateAdminUserRequest.password()),
                updateAdminUserRequest.role(),
                updateAdminUserRequest.authenticationStatus(),
                updateAdminUserRequest.nickname(),
                updateAdminUserRequest.failedLoginAttempts(),
                updateAdminUserRequest.lastLoginIp(),
                updateAdminUserRequest.lastLoginAt()
        );
        // 어드민 사용자 엔티티 UPDATE
        adminUserRepository.save(adminUser);
    }

    /**
     * 어드민 사용자 삭제
     * @param adminUserId 삭제할 어드민 사용자의 ID
     */
    @Override
    @Transactional
    public void delete(
            Long adminUserId
    ) {
        adminUserRepository.delete(getAdminUser(adminUserId));
    }
}