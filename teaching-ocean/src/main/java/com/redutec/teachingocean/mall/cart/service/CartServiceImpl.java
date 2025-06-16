package com.redutec.teachingocean.mall.cart.service;

import com.redutec.core.dto.InstituteCartDto;
import com.redutec.core.entity.Institute;
import com.redutec.core.entity.InstituteCart;
import com.redutec.core.entity.Teacher;
import com.redutec.core.mapper.InstituteCartMapper;
import com.redutec.core.repository.InstituteCartRepository;
import com.redutec.core.repository.TeacherRepository;
import com.redutec.teachingocean.authentication.service.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final InstituteCartMapper instituteCartMapper;
    private final InstituteCartRepository instituteCartRepository;
    private final AuthenticationService authenticationService;
    private final TeacherRepository teacherRepository;

    /**
     * 리딩오션몰 - 특정 상품 선택 - 장바구니에 추가
     *
     * @param addCartItemsRequests 장바구니(교육기관) 등록 정보를 담은 DTO List
     * @return 등록된 장바구니(교육기관) 정보
     */
    @Override
    @Transactional
    public InstituteCartDto.CartItemResponse addCartItems(
            List<InstituteCartDto.AddCartItemsRequest> addCartItemsRequests
    ) {
        // 현재 로그인한 교육기관이 생성한 장바구니 엔티티가 있는지 조회(보유한 장바구니가 없으면 새로운 장바구니 엔티티를 생성)
        Long teacherId = authenticationService.getAuthenticatedTeacher().teacherId();
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException("교사가 존재하지 않습니다. teacherId: " + teacherId));
        authenticationService.validateAuthenticationStatus(teacher);
        Long instituteId = Optional.ofNullable(teacher.getInstitute())
                .map(Institute::getId)
                .orElseThrow(() -> new EntityNotFoundException("교사가 소속된 교육기관이 없습니다. instituteId: " + teacher.getInstitute().getId()));
        InstituteCart instituteCart = instituteCartRepository.findByInstituteId(instituteId)
                .orElseGet(() -> InstituteCart.builder()
                        .instituteId(instituteId)
                        .build()
                );
        // 조회한 장바구니 엔티티에 소속할 상품들을 요청 객체에서 가져와 담기(상품과 수량)
        instituteCartMapper.toEntity(addCartItemsRequests, instituteCart);
        // 장바구니 엔티티를 저장하고 장바구니에 담긴 상품(상품정보와 수량)들을 담은 응답 객체를 리턴
        return instituteCartMapper.toResponseDto(instituteCartRepository.save(instituteCart));
    }

    /**
     * 리딩오션몰 - 장바구니
     *
     * @param getCartItemRequest 조회 조건을 담은 DTO
     * @return 현재 로그인한 교육기관의 장바구니에 담긴 상품 응답 객체
     */
    @Override
    @Transactional(readOnly = true)
    public InstituteCartDto.CartItemPageResponse getCartItems(InstituteCartDto.GetCartItemRequest getCartItemRequest) {
        return null;
    }

    /**
     * 리딩오션몰 - 장바구니 비우기
     */
    @Override
    @Transactional
    public void clearCart() {

    }
}