package com.redutec.admin.administrator.service;

import com.redutec.admin.administrator.dto.AdministratorDto;
import com.redutec.admin.administrator.mapper.AdministratorMapper;
import com.redutec.admin.config.JwtUtil;
import com.redutec.core.entity.Administrator;
import com.redutec.core.repository.AdministratorRepository;
import com.redutec.core.specification.AdministratorSpecification;
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
public class AdministratorServiceImpl implements AdministratorService {
    private final AdministratorMapper administratorMapper;
    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 시스템 관리자 등록
     * @param createAdministratorRequest 시스템 관리자 등록 정보를 담은 DTO
     * @return 등록된 시스템 관리자 정보
     */
    @Override
    @Transactional
    public AdministratorDto.AdministratorResponse create(
            AdministratorDto.CreateAdministratorRequest createAdministratorRequest
    ) {
        return administratorMapper.toResponseDto(
                administratorRepository.save(
                        administratorMapper.toEntity(
                                createAdministratorRequest
                        )
                )
        );
    }

    /**
     * 조건에 맞는 시스템 관리자 목록 조회
     * @param findAdministratorRequest 조회 조건을 담은 DTO
     * @return 조회된 시스템 관리자 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public AdministratorDto.AdministratorPageResponse find(
            AdministratorDto.FindAdministratorRequest findAdministratorRequest
    ) {
        return administratorMapper.toPageResponseDto(administratorRepository.findAll(
                AdministratorSpecification.findWith(administratorMapper.toCriteria(findAdministratorRequest)),
                (findAdministratorRequest.page() != null && findAdministratorRequest.size() != null)
                        ? PageRequest.of(findAdministratorRequest.page(), findAdministratorRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 시스템 관리자 조회
     * @param administratorId 시스템 관리자 고유번호
     * @return 특정 시스템 관리자 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public AdministratorDto.AdministratorResponse findById(
            Long administratorId
    ) {
        return administratorMapper.toResponseDto(getAdministrator(administratorId));
    }

    /**
     * 특정 시스템 관리자 엔티티 조회(닉네임으로 조회)
     * @param nickname 시스템 관리자 닉네임
     * @return 특정 시스템 관리자 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Administrator findByNickname(
            String nickname
    ) {
        return administratorRepository.findByNickname(nickname)
                .orElseThrow(() -> new EntityNotFoundException("No such administrator"));
    }

    /**
     * 특정 시스템 관리자 엔티티 조회
     * @param administratorId 시스템 관리자 고유번호
     * @return 특정 시스템 관리자 엔티티 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Administrator getAdministrator(
            Long administratorId
    ) {
        return administratorRepository.findById(administratorId)
                .orElseThrow(() -> new EntityNotFoundException("No such administrator"));
    }

    /**
     * 시스템 관리자 수정
     * @param administratorId 수정할 시스템 관리자의 ID
     * @param updateAdministratorRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Long administratorId,
            AdministratorDto.UpdateAdministratorRequest updateAdministratorRequest
    ) {
        // 수정할 시스템 관리자 엔티티 조회
        Administrator administrator = getAdministrator(administratorId);
        // UPDATE 도메인 메서드로 변환
        administrator.updateAdministrator(
                updateAdministratorRequest.email(),
                passwordEncoder.encode(updateAdministratorRequest.password()),
                updateAdministratorRequest.role(),
                updateAdministratorRequest.authenticationStatus(),
                updateAdministratorRequest.nickname(),
                updateAdministratorRequest.failedLoginAttempts(),
                updateAdministratorRequest.lastLoginIp(),
                updateAdministratorRequest.lastLoginAt()
        );
        // 시스템 관리자 엔티티 UPDATE
        administratorRepository.save(administrator);
    }

    /**
     * 시스템 관리자 삭제
     * @param administratorId 삭제할 시스템 관리자의 ID
     */
    @Override
    @Transactional
    public void delete(
            Long administratorId
    ) {
        administratorRepository.delete(getAdministrator(administratorId));
    }
}