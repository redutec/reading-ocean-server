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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final AdminUserMapper adminUserMapper;
    private final AdminUserRepository adminUserRepository;

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
        // 페이지 번호(page)와 페이지 크기(size)가 모두 null이 아니면 PageRequest 생성(그렇지 않으면 pageable 처리 하지 않음)
        Pageable pageable = Optional.ofNullable(findAdminUserRequest.page())
                .flatMap(page -> Optional.ofNullable(findAdminUserRequest.size())
                        .map(size -> (Pageable) PageRequest.of(page, size)))
                .orElse(Pageable.unpaged());
        // Specification, pageable을 사용하여 엔티티 목록 조회
        var adminUsers = adminUserRepository.findAll(
                AdminUserSpecification.findWith(adminUserMapper.toCriteria(findAdminUserRequest)),
                pageable
        );
        // 조회한 엔티티 목록을 응답 객체로 변환하여 리턴
        return adminUserMapper.toPageResponseDto(adminUsers);
    }

    /**
     * 특정 어드민 사용자 조회
     * @param adminUserId 어드민 사용자 고유번호
     * @return 특정 어드민 사용자 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public AdminUserDto.AdminUserResponse get(Long adminUserId) {
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
        adminUserMapper.updateEntity(getAdminUser(adminUserId), updateAdminUserRequest);
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