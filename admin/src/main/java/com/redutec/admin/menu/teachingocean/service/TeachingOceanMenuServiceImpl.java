package com.redutec.admin.menu.teachingocean.service;

import com.redutec.core.dto.TeachingOceanMenuDto;
import com.redutec.core.entity.TeachingOceanMenu;
import com.redutec.core.mapper.TeachingOceanMenuMapper;
import com.redutec.core.repository.TeachingOceanMenuRepository;
import com.redutec.core.specification.TeachingOceanMenuSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TeachingOceanMenuServiceImpl implements TeachingOceanMenuService {
    private final TeachingOceanMenuMapper teachingOceanMenuMapper;
    private final TeachingOceanMenuRepository teachingOceanMenuRepository;

    /**
     * 티칭오션 메뉴 등록
     * @param createTeachingOceanMenuRequest 티칭오션 메뉴 등록 정보를 담은 DTO
     * @return 등록된 티칭오션 메뉴 정보
     */
    @Override
    @Transactional
    public TeachingOceanMenuDto.TeachingOceanMenuResponse create(
            TeachingOceanMenuDto.CreateTeachingOceanMenuRequest createTeachingOceanMenuRequest
    ) {
        // 등록 요청 객체에 상위 메뉴 고유번호가 존재하면 상위 메뉴 엔티티를 조회
        TeachingOceanMenu parentMenu = Optional.ofNullable(createTeachingOceanMenuRequest.parentMenuId())
                .map(this::getTeachingOceanMenu)
                .orElse(null);
        // 등록 요청 객체에 하위 메뉴 고유번호 목록이 존재하면 하위 메뉴 엔티티들을 조회
        List<TeachingOceanMenu> childrenMenus = Optional.ofNullable(createTeachingOceanMenuRequest.childrenMenuIds())
                .orElse(List.of())
                .stream()
                .map(this::getTeachingOceanMenu)
                .toList();
        // 티칭오션 메뉴 등록
        return teachingOceanMenuMapper.toResponseDto(teachingOceanMenuRepository.save(teachingOceanMenuMapper.toCreateEntity(
                createTeachingOceanMenuRequest,
                parentMenu,
                childrenMenus
        )));
    }

    /**
     * 조건에 맞는 티칭오션 메뉴 목록 조회
     * @param findTeachingOceanMenuRequest 조회 조건을 담은 DTO
     * @return 조회된 티칭오션 메뉴 목록 및 페이징 정보
     */
    @Override
    @Transactional(readOnly = true)
    public TeachingOceanMenuDto.TeachingOceanMenuPageResponse find(
            TeachingOceanMenuDto.FindTeachingOceanMenuRequest findTeachingOceanMenuRequest
    ) {
        return teachingOceanMenuMapper.toPageResponseDto(teachingOceanMenuRepository.findAll(
                TeachingOceanMenuSpecification.findWith(teachingOceanMenuMapper.toCriteria(findTeachingOceanMenuRequest)),
                (findTeachingOceanMenuRequest.page() != null && findTeachingOceanMenuRequest.size() != null)
                        ? PageRequest.of(findTeachingOceanMenuRequest.page(), findTeachingOceanMenuRequest.size())
                        : Pageable.unpaged()));
    }

    /**
     * 특정 티칭오션 메뉴 조회
     * @param teachingOceanMenuId 티칭오션 메뉴 고유번호
     * @return 특정 티칭오션 메뉴 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public TeachingOceanMenuDto.TeachingOceanMenuResponse findById(Long teachingOceanMenuId) {
        return teachingOceanMenuMapper.toResponseDto(getTeachingOceanMenu(teachingOceanMenuId));
    }

    /**
     * 티칭오션 메뉴 수정
     * @param teachingOceanMenuId 수정할 티칭오션 메뉴의 ID
     * @param updateTeachingOceanMenuRequest 수정할 정보를 담은 DTO
     */
    @Override
    @Transactional
    public void update(
            Long teachingOceanMenuId,
            TeachingOceanMenuDto.UpdateTeachingOceanMenuRequest updateTeachingOceanMenuRequest
    ) {
        // 수정 요청 객체에 상위 메뉴 고유번호가 존재하면 상위 메뉴 엔티티 조회
        TeachingOceanMenu parentMenu = Optional.ofNullable(updateTeachingOceanMenuRequest.parentMenuId())
                .map(this::getTeachingOceanMenu)
                .orElse(null);
        // 수정 요청 객체에 하위 메뉴 고유번호 목록이 존재하면 하위 메뉴 엔티티들을 조회
        List<TeachingOceanMenu> childrenMenus = Optional.ofNullable(updateTeachingOceanMenuRequest.childrenMenuIds())
                .map(childrenMenuIds -> childrenMenuIds.stream()
                        .map(this::getTeachingOceanMenu)
                        .toList())
                .orElse(null);
        // 티칭오션 메뉴 수정
        teachingOceanMenuRepository.save(teachingOceanMenuMapper.toUpdateEntity(
                getTeachingOceanMenu(teachingOceanMenuId),
                updateTeachingOceanMenuRequest,
                parentMenu,
                childrenMenus
        ));
    }

    /**
     * 티칭오션 메뉴 삭제
     * @param teachingOceanMenuId 삭제할 티칭오션 메뉴의 ID
     */
    @Override
    @Transactional
    public void delete(Long teachingOceanMenuId) {
        teachingOceanMenuRepository.delete(getTeachingOceanMenu(teachingOceanMenuId));
    }

    /**
     * 특정 티칭오션 메뉴 엔티티 조회
     * @param teachingOceanMenuId 티칭오션 메뉴 고유번호
     * @return 특정 티칭오션 메뉴 엔티티 객체
     */
    @Transactional(readOnly = true)
    public TeachingOceanMenu getTeachingOceanMenu(Long teachingOceanMenuId) {
        return teachingOceanMenuRepository.findById(teachingOceanMenuId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 티칭오션 메뉴입니다. teachingOceanMenuId = " + teachingOceanMenuId));
    }
}